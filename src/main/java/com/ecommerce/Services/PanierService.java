package com.ecommerce.Services;

import java.time.Clock;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.ecommerce.Entities.Status;
import com.ecommerce.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.Entities.Client;
import com.ecommerce.Entities.Panier;
import com.ecommerce.Entities.Product;
import com.ecommerce.Repository.ClientRepo;
import com.ecommerce.Repository.PanierRepo;

@Service
public class PanierService {

	@Autowired
	PanierRepo panierRepo;
	
	@Autowired 
	ClientRepo clientRepo;

	@Autowired
	ProductRepo productRepo;
	
	
	
	
	public Panier panier(Long idC, Long id) {
		Client c = clientRepo.findById(idC).orElseThrow(()->new RuntimeException(String.format("no client with %s as id",idC)));
		Product p = productRepo.findById(id).orElseThrow(()->new RuntimeException(String.format("no product with %s as id",id)));
		for(Panier x : panierRepo.findAll()){
			if(x.getProduct().equals(p) && x.getClient().equals(c)){
				update(x.getId(), x.getQuantity()+1);
				return x;
			}
		}
		Panier panier = new Panier();
		panier.setClient(c);
		panier.setProduct(p);
		panier.setQuantity(1L);
		panier.setStatus(Status.Pending);
		panier.setTimestamp(ZonedDateTime.now(Clock.systemUTC()));
		c.getCommands().add(panier);
		return panierRepo.save(panier);
	}
	
	
	public void update(Long id, Long quantity) {
		Panier p = panierRepo.findById(id).orElseThrow(()->new RuntimeException(String.format("no panier with %s as id",id)));
		p.setQuantity(quantity);
		p.setTimestamp(ZonedDateTime.now(Clock.systemUTC()));
		panierRepo.save(p);
		
	}

	public void updateStatus(Long id,Long quantity,  String status){
		Panier p = panierRepo.findById(id).orElseThrow(()->new RuntimeException(String.format("no panier with %s as id",id)));
		if(status.toLowerCase().equals("processing")){
			p.setStatus(Status.Processing);
		}
		if(status.toLowerCase().equals("completed")){
			p.setStatus(Status.Completed);
		}
		if(status.toLowerCase().equals("cancelled")){
			p.setStatus(Status.Cancelled);
		}
		if(status.toLowerCase().equals("shipped")){
			p.setStatus(Status.Shipped);
		}
		p.setQuantity(quantity);
		panierRepo.save(p);
	}
	
	public void delete(Long id) {
		Panier p = panierRepo.findById(id).orElseThrow(()->new RuntimeException(String.format("no panier with %s as id",id)));
		Client c = p.getClient();
		List<Panier> list =  c.getCommands();
		List<Panier> cart = new ArrayList<>();
		for(Panier k : list) {
			if(!k.equals(p))
				cart.add(k);
		}
		c.setCommands(cart);
		clientRepo.save(c);
		p.setClient(null);
		panierRepo.deleteById(id);
	}

	public List<Panier> paniers(){
		return panierRepo.findAll();
	}
	
	
}
