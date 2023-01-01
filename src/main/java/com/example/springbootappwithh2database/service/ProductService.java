package com.example.springbootappwithh2database.service;

import com.example.springbootappwithh2database.entity.Product;

public interface ProductService {

   public Product getProductById(int id );

   public Product insertProduct (Product product);
}
