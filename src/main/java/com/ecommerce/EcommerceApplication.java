package com.ecommerce;

import com.ecommerce.Entities.Category;
import com.ecommerce.Entities.Client;
import com.ecommerce.Entities.Permission;
import com.ecommerce.Entities.Product;
import com.ecommerce.Repository.CategoryRepo;
import com.ecommerce.Repository.PermissionRepo;
import com.ecommerce.Repository.ProductRepo;
import com.ecommerce.Services.ClientService;
import com.ecommerce.dto.ClientDTO;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(ClientService clientService, PermissionRepo permissionRepo, ProductRepo productRepo,
										CategoryRepo categoryRepo){
		return result ->{
			Permission p = new Permission();
			p.setName("USER");

			Permission p1 = new Permission();
			p1.setName("ADMIN");

			permissionRepo.save(p);   permissionRepo.save(p1);

			/*Client c = new Client();
			c.setUsername("Anass123");
			c.setTel("0608539556");
			c.setEmail("Anass@gmail.com");
			c.setName("Anass");
			c.setPassword("Ana123ss456");
			//c.getRoles().add(p);*/
			ClientDTO client = new ClientDTO("Anass123",
					"Anass",
					"Anass@gmail.com",
					"Ana123ss456",
					"Sale Laayayda",
					"0608539556");
			ClientDTO client1 = new ClientDTO("Ayman15",
					"Anass",
					"Anass@gmail.com",
					"Ana123ss456",
					"Sale Laayayda",
					"0608539556");
			clientService.createUser(client);
			clientService.createUser(client1);
			clientService.affectRole(1L);

			categoryRepo.save(Category.builder()
							.id(1L)
							.name("TV")
					.build());
			categoryRepo.save(Category.builder()
					.id(2L)
					.name("SMARTPHONE")
					.build());
			categoryRepo.save(Category.builder()
					.id(3L)
					.name("T-SHIRT")
					.build());
			categoryRepo.save(Category.builder()
					.id(4L)
					.name("JACKET")
					.build());
			productRepo.save(Product.builder()
							.id(1L)
							.img("/assets/TV.jpg")
							.description("buy if you can afford it")
							.rating(3.9)
							.category(categoryRepo.findById(1L).get())
							.name("TV 32 pouce")
							.price(180L)
					.build());
			productRepo.save(Product.builder()
							.id(2L)
					.img("/assets/tv65.jpg")
					.description("buy if you can afford it")
					.rating(4.5)
					.name("TV 65 pouce")
					.category(categoryRepo.findById(1L).get())
					.price(280L)
					.build());
			productRepo.save(Product.builder()
					.id(3L)
					.img("/assets/iphone13.jpg")
					.description("buy if you can afford it")
					.rating(4.2)
					.name("iphone 13")
					.category(categoryRepo.findById(2L).get())
					.price(780L)
					.build());
			productRepo.save(Product.builder()
							.id(4L)
					.img("/assets/iphone14max.jpg")
					.description("buy if you can afford it")
					.rating(2.9)
					.name("iphone 14 pro max")
					.category(categoryRepo.findById(2L).get())
					.price(980L)
					.build());
			productRepo.save(Product.builder()
							.id(5L)
					.img("/assets/jacket1.jpg")
					.description("buy if you can afford it")
					.rating(3.2)
					.name("jacket 1")
					.category(categoryRepo.findById(4L).get())
					.price(150L)
					.build());
			productRepo.save(Product.builder()
							.id(6L)
					.img("/assets/jacket2.jpg")
					.description("buy if you can afford it")
					.rating(3.5)
					.name("jacket 2")
					.category(categoryRepo.findById(4L).get())
					.price(140L)
					.build());

		};
	}

}
