package com.example.springbootappwithh2database.controller;

import com.example.springbootappwithh2database.entity.Product;
import com.example.springbootappwithh2database.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {


    @Autowired
    private ProductService productService;
    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable int id ){
      return   productService.getProductById(id);

    }

    @PostMapping("/insert")
    public Product insert(@RequestBody Product product ){
        return   productService.insertProduct(product);
    }
}
