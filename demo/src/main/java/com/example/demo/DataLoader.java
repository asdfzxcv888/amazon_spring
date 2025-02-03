package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String...args)throws Exception{

    }

    // @Override
    // public void run(String... args) throws Exception {
    //     // Parse JSON file
    //     ObjectMapper mapper = new ObjectMapper();
    //     List<Product> products = mapper.readValue(
    //             new File("G:\\ww\\demo\\src\\main\\java\\com\\example\\demo\\data\\merged_data.json"),
    //             mapper.getTypeFactory().constructCollectionType(List.class, Product.class)
    //     );

    //     // Save products to the database, avoiding duplicates
    //     for (Product product : products) {
    //         if (!productRepository.existsByName(product.getName())) {
    //             productRepository.save(product);
    //         } else {
    //             System.out.println("Duplicate product skipped: " + product.getName());
    //         }
    //     }

    //     System.out.println("Data loaded successfully!");
    // }
}
