package com.ecommerce.dto;

import com.ecommerce.Entities.Category;

public record ProductDTO(String name,
                         String img,
                         String Description,
                         double rating,
                         Long price,
                         String category) {
}
