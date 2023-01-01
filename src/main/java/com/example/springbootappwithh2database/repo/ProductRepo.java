package com.example.springbootappwithh2database.repo;

import com.example.springbootappwithh2database.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product , Integer> {
}
