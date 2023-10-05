package com.ecommerce.Controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ecommerce.Entities.Client;
import com.ecommerce.Entities.Product;
import com.ecommerce.dto.ClientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import com.ecommerce.Services.ClientService;
import com.ecommerce.Services.PanierService;
import com.ecommerce.Services.ProductService;

@RestController()
@RequestMapping("/Rest")
public class secondControl {

	  @Autowired
	    ClientService clientService;

	    @Autowired
	     ProductService productService;
	    
	    @Autowired
	    PanierService panierService;
	    
	    @Autowired
	    AuthenticationManager authenticationManager;
	    
	    @Autowired
	    JwtEncoder jwtEncoder;
	
	@PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request){
    	try {
    		System.out.print(request);
    		UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(
    				request.get("username"),request.get("password"));
    		Authentication auth = authenticationManager.authenticate(user);
    		
    		String scope = auth.getAuthorities().stream().map(p -> p.getAuthority()).collect(Collectors.joining(" "));
    		Instant instant = Instant.now();
    		JwtClaimsSet jwt = JwtClaimsSet.builder()
    				.issuedAt(instant)
    				.expiresAt(instant.plus(10, ChronoUnit.MINUTES))
    				.subject(request.get("username"))
    				.claim("scope", scope)
    				.build();
    		
    		JwtEncoderParameters jw = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS512).build(), jwt);
    		String token = jwtEncoder.encode(jw).getTokenValue();
    		System.out.print(token);
    		return Map.of("access_token",token);
    	}catch(Exception e) {
    		e.printStackTrace();
			throw new RuntimeException(e.getMessage());

    	}
    	
    }


	@PostMapping("/createClient")
	public Client client(@RequestBody ClientDTO c) throws Exception{

		return clientService.createUser(c);
	}
	@GetMapping("/products")
	public List<Product> products(){
		return productService.Products();
	}

	@GetMapping("/searchProductsByName/{name}")
	public List<Product> productsByName(@PathVariable("name") String name){
		return productService.productsName(name);
	}

	@GetMapping("/getProductById/{id}")
	public Product prod(@PathVariable("id") Long id){
		return productService.getProduct(id);
	}



	@GetMapping("/getProductsByCategory/{name}")
	public List<Product> productsN(@PathVariable("name") String name){
		return productService.productsCa(name);
	}

	@GetMapping("/userProfile/{name}")
	public Client profile(@PathVariable("name") String username) {
		return clientService.username(username);
	}
}
