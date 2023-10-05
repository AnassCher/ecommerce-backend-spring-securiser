package com.ecommerce.Services;

import com.ecommerce.Entities.Category;
import com.ecommerce.Entities.Product;
import com.ecommerce.Repository.CategoryRepo;
import com.ecommerce.Repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepo categoryRepo;

    @Autowired
    ProductRepo productRepo;

    public Category createCategory(String name){
        Category cat = new Category();
        cat.setName(name);
        return categoryRepo.save(cat);
    }

    public void updateCategory(Long id,String name){
        Category cat = categoryRepo.findById(id).orElseThrow(()->new RuntimeException(String.format("Category not found %s",id)));
        cat.setName(name);
        categoryRepo.save(cat);
    }

    public void delete(Long id){
        Category cat = categoryRepo.findById(id).orElseThrow(()->new RuntimeException(String.format("Category not found %s",id)));
        for(Product p : productRepo.findAll()){
            if(p.getCategory().getName().equals(cat.getName())){
                productRepo.delete(p);
            }
        }
        categoryRepo.deleteById(id);
    }

    public List<Category> categoryList(String name){
        List<Category> Res = new ArrayList<>();
        for(Category c : categoryRepo.findAll()){
            if(c.getName().toLowerCase().contains(name.toLowerCase())){
                Res.add(c);
            }
        }
        return Res;
    }
}
