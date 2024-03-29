package com.ecommerce.Repository;

import com.ecommerce.Entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepo extends JpaRepository<Client, Long> {
    Client findByUsername(String Username);

}
