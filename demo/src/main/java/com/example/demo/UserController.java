package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


   @Autowired
    private UserRepository repo;

    @Autowired
    private JwtUtil jwtUtil;



    @PostMapping("/adduser")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            user.encode();
            System.out.println("method called  user");
            User savedUser = repo.save(user);
            System.out.println("saved user");
            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            // Log the error
            System.err.println("Error saving user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving user");
        }
    }



    // @PostMapping("/login")
    // public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
    //     String username = request.get("name");
    //     String password = request.get("password");
    
    //     if (username == null || password == null) {
    //         return ResponseEntity.badRequest().body("Username and password are required.");
    //     }
    
    //     // Find the user by username
    //     User user = repo.findUserByName(username);
    
    //     if (user == null) {
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
    //     }
    
    //     // Validate the password (Assuming passwords are stored as plain text - not recommended)
    //     BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
    //     if (!passwordEncoder.matches(password, user.getpassword())) {
    //         System.err.println("pwd "+user.getpassword());
    //         System.out.println("encoded "+passwordEncoder.encode(password));
    //         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
    //     }
    
    //    Map<String, Object> response = new HashMap<>();
    //     response.put("message", "Login successful!");
    //     response.put("user", user);

    //     return ResponseEntity.ok(response);
    // }

    

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
    String username = request.get("name");
    String password = request.get("password");

    if (username == null || password == null) {
        return ResponseEntity.badRequest().body("Username and password are required.");
    }

    // Find the user by username
    User user = repo.findUserByName(username);

    if (user == null) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
    }

    // Validate the password
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    if (!passwordEncoder.matches(password, user.getpassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password.");
    }

    // Generate JWT token
    String token = jwtUtil.generateToken(username);

    // Build the response
    Map<String, Object> response = new HashMap<>();
    response.put("message", "Login successful!");
    response.put("token", token);
    response.put("user", user);

    return ResponseEntity.ok(response);
}


    @GetMapping("/user")
    public String hello(){
        System.out.println("hello");

        return "user";
    }
}
