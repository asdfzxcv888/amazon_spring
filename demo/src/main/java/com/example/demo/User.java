package com.example.demo;

import jakarta.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;



@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) // Makes the name field unique
    private String name;

    private String password;


    public void setid(Long k){
        id=k;
        
    }

    public Long getid(){
        return id;
        
    }

    public void setpassword(String pwd){
        this.password=pwd;
    }


    public String getpassword(){
        return password;
    }

    public void setname(String k){
        name=k;
        
    }

    public String getname(){
        return name;
        
    }

    public User(){

    }

    public User(Long x,String y,String z){
            id=x;
            name=y;
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            password=passwordEncoder.encode(z);
            System.out.println("password is"+password);
    }



    public void encode(){   
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            this.password=passwordEncoder.encode(this.password);
      

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


}
