// package com.example.demo;

// import jakarta.persistence.Id;

// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// // import jakarta.persistence.GeneratedValue;
// // import jakarta.persistence.GenerationType;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;



// @Entity
// public class User {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(unique = true, nullable = false) // Makes the name field unique
//     private String name;

//     private String password;


//     public void setid(Long k){
//         id=k;
        
//     }

//     public Long getid(){
//         return id;
        
//     }

//     public void setpassword(String pwd){
//         this.password=pwd;
//     }


//     public String getpassword(){
//         return password;
//     }

//     public void setname(String k){
//         name=k;
        
//     }

//     public String getname(){
//         return name;
        
//     }

//     public User(){

//     }

//     public User(Long x,String y,String z){
//             id=x;
//             name=y;
//             BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//             password=passwordEncoder.encode(z);
//             System.out.println("password is"+password);
//     }



//     public void encode(){   
//         BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//             this.password=passwordEncoder.encode(this.password);
      

//     }

//     @Override
//     public String toString() {
//         return "User{" +
//                 "id=" + id +
//                 ", name='" + name + '\'' +
//                 '}';
//     }


// }



package com.example.demo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) // Makes the name field unique
    private String name;

    @Column(unique = true, nullable = false) // Makes the name field unique
    private String email;

    private String password;

    // One-to-Many relationship with Order
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore 
    private List<Order> orders = new ArrayList<>();

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


    public String getEmail() {
        return name;
    }

    public void setEmail(String name) {
        this.email = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    // Helper methods to manage orders
    public void addOrder(Order order) {
        orders.add(order);
        order.setUser(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setUser(null);
    }

    public void encode() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(this.password);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public User() {
    }

    public User(Long id, String name, String password,String email) {
        this.id = id;
        this.name = name;
        this.email=email;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }
}
