package com.ecommerce.Repository;

import com.ecommerce.Entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepo extends JpaRepository<Permission, Long> {
    Permission findByName(String name);
}
