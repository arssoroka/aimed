package com.example.springbootappwithh2database.service.impl;

import com.example.springbootappwithh2database.entity.Product;
import com.example.springbootappwithh2database.repo.ProductRepo;
import com.example.springbootappwithh2database.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public Product getProductById(int id) {
        return productRepo.findById(id).get();
    }

    @Override
    public Product insertProduct(Product product) {
        return productRepo.save(product);
    }
}
