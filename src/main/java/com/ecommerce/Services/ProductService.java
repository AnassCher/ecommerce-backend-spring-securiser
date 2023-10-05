package com.ecommerce.Services;

import com.ecommerce.Entities.Category;
import com.ecommerce.Entities.Product;
import com.ecommerce.Repository.CategoryRepo;
import com.ecommerce.Repository.ProductRepo;
import com.ecommerce.dto.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ProductService {

    @Autowired
    ProductRepo productRepo;

    @Autowired
    CategoryRepo categoryRepo;


    public Product createProduct(ProductDTO product){
        Product p = new Product();
        Category ca = categoryRepo.findByName(product.category()).orElseThrow(() ->  new RuntimeException(String.format("Category %s not found",product.category())));
        p.setName(product.name());
        p.setImg(product.img());
        p.setRating(product.rating());
        p.setDescription(product.Description());
        p.setPrice(product.price());
        p.setCategory(ca);
        return productRepo.save(p);
    }

    public void updateProduct(Long id, ProductDTO product){
        productRepo.findById(id).orElseThrow(()-> new RuntimeException(String.format("no product with %s as id",id)));
        Category ca = categoryRepo.findByName(product.category()).orElseThrow(() ->  new RuntimeException(String.format("Category %s not found",product.category())));
        Product p = Product.builder()
                .id(id)
                .description(product.Description())
                .name(product.name())
                .img(product.img())
                .price(product.price())
                .rating(product.rating())
                .category(ca)
                .build();
        productRepo.save(p);
    }

    public void deleteProduct(Long id){
        productRepo.findById(id).orElseThrow(()-> new RuntimeException(String.format("no product with %s as id",id)));
        productRepo.deleteById(id);
    }

    public Product getProduct(Long id){
        return productRepo.findById(id).orElseThrow(()-> new RuntimeException(String.format("no product with %s as id",id)));
    }

    public List<Product> Products(){
        return productRepo.findAll();
    }

    public List<Product> productsCa(String name){
        Category ca = categoryRepo.findByName(name).orElseThrow(() ->  new RuntimeException(String.format("Category %s not found",name)));
        Integer count =0;
        Integer limit = 6;
        List<Product> list = new ArrayList<>();
        List<Product> data = productRepo.findAll();
        for(Product x : data){
            if(x.getCategory().equals(ca)){
                list.add(x);
                count++;

                if(count >= limit)
                    break;
            }
        }
        return list;
    }

    public List<Product> productsName(String name){

        List<Product> res = new ArrayList<>();
        for(Product c : productRepo.findAll()){
            if(c.getName().toLowerCase().contains(name.toLowerCase())){
                res.add(c);
            }
        }
        if(res.size() == 0){
            for(Category c : categoryRepo.findAll()){
                if(c.getName().toLowerCase().contains(name.toLowerCase())){
                    for(Product p : productRepo.findAll()){
                        if(p.getCategory().getName().equals(c.getName())){
                            if(!res.contains(p)) {
                                res.add(p);
                            }
                        }
                    }
                }
            }
        }
        return res;
    }
}
