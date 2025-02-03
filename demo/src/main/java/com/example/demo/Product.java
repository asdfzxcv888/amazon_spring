package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantityLeft;

    @Column(nullable = false)
    private double price;

    @Column(length = 500)
    private String description;

    @JsonProperty("image_url") // Maps JSON's "image_url" to this field
    private String image_url;



    // Default constructor (required by JPA)
    public Product() {
    }

    // Parameterized constructor for easier initialization
    public Product(String name, int quantityLeft, double price, String description,String image_url) {
        this.name = name;
        this.quantityLeft = quantityLeft;
        this.price = price;
        this.description = description;
        this.image_url=image_url;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantityLeft() {
        return quantityLeft;
    }

    public void setQuantityLeft(int quantityLeft) {
        this.quantityLeft = quantityLeft;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image_url;
    }

    public void setImage(String image_url) {
        this.image_url = image_url;
    }

    // toString method for better debugging
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantityLeft=" + quantityLeft +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", image='" + image_url + '\'' +
                '}';
    }
}
