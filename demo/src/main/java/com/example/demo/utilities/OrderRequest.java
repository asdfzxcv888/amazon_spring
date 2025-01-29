package com.example.demo.utilities;


import java.util.List;

public class OrderRequest {
    private String orderDate;
    private List<String> productNames;

    // Getters and Setters
    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public List<String> getProductNames() {
        for (String string : productNames) {
            System.out.println("items are "+string);
        }
        return productNames;
    }

    public void setProductNames(List<String> productNames) {
       
        this.productNames = productNames;
    }

   
}
