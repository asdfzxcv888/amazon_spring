package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.utilities.ApiResponse;

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
            return ResponseEntity.ok(new ApiResponse<>("User added successfully!", savedUser));
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

        System.out.println("login details name"+username);
        System.out.println("login details pwd"+password);

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
        if (!passwordEncoder.matches(password, user.getPassword())) {
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


  @PutMapping("/updateuser")
    public ResponseEntity<?> updateUser(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("name");
            String newPassword = request.get("password");

            if (username == null || newPassword == null) {
                return ResponseEntity.badRequest().body("Username and new password are required.");
            }

            User user = repo.findUserByName(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(newPassword));
            User updatedUser = repo.save(user);

            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            System.err.println("Error updating user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user");
        }
    }

    // Delete user by username
    @DeleteMapping("/deleteuser/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        try {
            User user = repo.findUserByName(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

            repo.delete(user);
            return ResponseEntity.ok(new ApiResponse<>("User added successfully!", user));
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
    }

    // Reset password (Forgot Password)
    @PostMapping("/forgotpassword")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("name");
            String newPassword = request.get("newPassword");

            if (username == null || newPassword == null) {
                return ResponseEntity.badRequest().body("Username and new password are required.");
            }

            User user = repo.findUserByName(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(newPassword));
            repo.save(user);

            return ResponseEntity.ok("Password reset successfully.");
        } catch (Exception e) {
            System.err.println("Error resetting password: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error resetting password");
        }
    }



    @GetMapping("/user")
    public String hello(){
        System.out.println("hello");

        return "user";
    }
}
