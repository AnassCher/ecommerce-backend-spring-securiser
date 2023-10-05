package com.ecommerce.Controller;

import com.ecommerce.Entities.Category;
import com.ecommerce.Entities.Client;
import com.ecommerce.Entities.Panier;
import com.ecommerce.Entities.Product;
import com.ecommerce.Repository.CategoryRepo;
import com.ecommerce.Services.CategoryService;
import com.ecommerce.Services.ClientService;
import com.ecommerce.Services.PanierService;
import com.ecommerce.Services.ProductService;
import com.ecommerce.dto.ClientDTO;

import com.ecommerce.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.jwt.JwtEncoder;

import org.springframework.stereotype.Controller;

import java.util.List;


@Controller
public class controller{
    @Autowired
    ClientService clientService;

    @Autowired
     ProductService productService;
    
    @Autowired
    PanierService panierService;

    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    CategoryService categoryService;
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    JwtEncoder jwtEncoder;

    @QueryMapping
    public List<Client> Clients(){
        return clientService.Clients();
    }

//    @MutationMapping
//    public Client client(@Argument ClientDTO c) throws Exception{
//
//        return clientService.createUser(c);
//    }
    @MutationMapping
    public void ChangePassword(@Argument Long id, @Argument String old, @Argument String newPass){
        this.clientService.ChangePassword(id,old,newPass);
    }

    @MutationMapping
    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteC(@Argument Long id) {
    	 clientService.deleteUser(id);
    }

    @MutationMapping
    public void updateC(@Argument Long id,@Argument ClientDTO c) {
    	clientService.updateUser(id,c);
    }

    @QueryMapping
    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Client getC(@Argument String username) {
    	return clientService.username(username);
    }

    @QueryMapping
    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Client user(@Argument Long id) {
    	return clientService.client(id);
    }

    @MutationMapping
    public Client auth(@Argument Long id){
        return clientService.affectRole(id);
    }
    
    @QueryMapping
    public List<Client> byUsername(@Argument String username){
        return clientService.byUsername(username);
    }
    /****************************************/
    /****************************************/
    /****************************************/
    /****************************************/


    @MutationMapping
    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Product product(@Argument ProductDTO p){
        return productService.createProduct(p);
    }

    @MutationMapping
    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void update(@Argument Long id, @Argument ProductDTO p) {
    	productService.updateProduct(id,p);
    }
    
    @MutationMapping
    //@PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void delete(@Argument Long id) {
    	productService.deleteProduct(id);
    } 
    
    
//    @QueryMapping
//    public List<Product> productsByName(@Argument String name){
//        return productService.productsName(name);
//    }
//
//    @QueryMapping
//    public Product prod(@Argument Long id){
//    	return productService.getProduct(id);
//    }
//
//    @QueryMapping
//    public List<Product> products(){
//        return productService.Products();
//    }
//
//    @QueryMapping
//    public List<Product> productsN(@Argument String name){
//        return productService.productsCa(name);
//    }
    
    /****************************************/
    /****************************************/
    /****************************************/
    /****************************************/
   
    @QueryMapping
    public List<Panier> paniers(){
        return panierService.paniers();
    }

    @MutationMapping
    public Panier panier(@Argument Long idC, @Argument Long id) {
    	return panierService.panier(idC, id);
    }
    
    @MutationMapping
    public void deleteI(@Argument Long id) {
    	panierService.delete(id);
    }
    
    @MutationMapping
    public void updateI(@Argument Long id,@Argument Long quantity) {
    	panierService.update(id,quantity);
    }

    @MutationMapping
    public void updateStatus(@Argument Long id,@Argument Long quantity, @Argument String status){
        panierService.updateStatus(id,quantity, status);
    }
    
    /****************************************/
    /****************************************/
    /****************************************/
    /****************************************/
    
    
    @QueryMapping
    public int numbU(){

        return clientService.Clients().size();
    }

    @QueryMapping
    public int numbP(){

        return productService.Products().size();
    }

    @QueryMapping
    public int numbO(){
        return panierService.paniers().size();
    }

    @QueryMapping
    public Long Earns(){
        List<Panier> e =panierService.paniers();
        Long s =0L;
        for(Panier x : e){
            s += x.getQuantity() * x.getProduct().getPrice();
        }
        return s;
    }




    /****************************************/
    /****************************************/
    /****************************************/
    /****************************************/

    @QueryMapping
    public List<Category> categories(){
        return categoryRepo.findAll();
    }

    @MutationMapping
    public Category category(@Argument String name){
        return categoryService.createCategory(name);
    }

    @MutationMapping
    public void updateCategory(@Argument Long id,@Argument String name){
        categoryService.updateCategory(id,name);
    }

    @MutationMapping
    public void deleteCategory(@Argument Long id){
        categoryService.delete(id);
    }

    @QueryMapping
    public List<Category> categoryList(@Argument String name){
        return categoryService.categoryList(name);
    }
}
