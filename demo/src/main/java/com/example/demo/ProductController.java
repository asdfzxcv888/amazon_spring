package com.example.demo;

import java.lang.reflect.Array;
import java.util.List;

import org.hibernate.dialect.function.array.ArrayToStringFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    

    @Autowired
    private ProductRepository prodrepo;


        @GetMapping("/products")
        public ResponseEntity<List<Product>> getAllProducts() {
            try {
                List<Product> products = prodrepo.findAll();
                return ResponseEntity.ok(products);
            } catch (Exception e) {
                // Log the error and return a 500 response
                System.err.println("Error fetching products: " + e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }

        




}
