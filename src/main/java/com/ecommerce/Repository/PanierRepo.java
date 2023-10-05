package com.ecommerce.Repository;

import com.ecommerce.Entities.Panier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanierRepo extends JpaRepository<Panier, Long> {
}
