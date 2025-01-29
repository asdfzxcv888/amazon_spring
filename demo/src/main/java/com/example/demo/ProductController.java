package com.example.demo;

import java.lang.reflect.Array;
import java.util.List;

import org.hibernate.dialect.function.array.ArrayToStringFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.utilities.OrderRequest;
import com.example.demo.utilities.OrderRequestItem;

@RestController
public class ProductController {
    

    @Autowired
    private ProductRepository prodrepo;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

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


     // Place an order using product names
    @PostMapping("/orderitem")
    public ResponseEntity<?> placeOrder(@RequestBody OrderRequest orderRequest, @RequestAttribute("username") String username) {
        try {
            // Fetch the user by username
             if (orderRequest.getProductNames() == null || orderRequest.getProductNames().isEmpty()) {
            return ResponseEntity.badRequest().body("Product names are required to place an order.");
        }
            User user = userRepository.findUserByName(username);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            // Create a new order for the user
            Order order = new Order();
            order.setUser(user);
            order.setOrderDate(orderRequest.getOrderDate());

            // Retrieve product details from the database using product names
            for (String productName : orderRequest.getProductNames()) {
                Product product = prodrepo.findByName(productName);
                if (product == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Product not found: " + productName);
                }

                // Add product to the order
                OrderItem orderItem = new OrderItem();
                orderItem.setProductName(product.getName());
                orderItem.setPrice(product.getPrice());
                orderItem.setQuantity(1); // Default quantity to 1 (can be extended)
                order.addOrderItem(orderItem);
            }

            // Save the order
            orderRepository.save(order);

            return ResponseEntity.ok("Order placed successfully!");
        } catch (Exception e) {
            System.err.println("Error placing order: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error placing order");
        }
    }


     @PutMapping("/modifyorder/{orderId}")
    public ResponseEntity<?> modifyOrder(@PathVariable Long orderId, @RequestBody OrderRequest orderRequest) {
        try {
            // Fetch the order by ID
            Order order = orderRepository.findById(orderId).orElse(null);
            if (order == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
            }

            // Modify the order by adding or removing items
            for (String productName : orderRequest.getProductNames()) {
                Product product = prodrepo.findByName(productName);
                if (product == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found: " + productName);
                }

                // Add the product if not already in the order
                boolean exists = order.getOrderItems().stream()
                        .anyMatch(item -> item.getProductName().equals(productName));
                if (!exists) {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setProductName(product.getName());
                    orderItem.setPrice(product.getPrice());
                    orderItem.setQuantity(1); // Default to 1
                    order.addOrderItem(orderItem);
                }
            }

            // Save the modified order
            orderRepository.save(order);
            return ResponseEntity.ok("Order modified successfully!");
        } catch (Exception e) {
            System.err.println("Error modifying order: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error modifying order");
        }
    }

    // Cancel an order
    @DeleteMapping("/cancelorder/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        try {
            // Fetch the order by ID
            Order order = orderRepository.findById(orderId).orElse(null);
            if (order == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found");
            }

            // Delete the order
            orderRepository.delete(order);
            return ResponseEntity.ok("Order canceled successfully!");
        } catch (Exception e) {
            System.err.println("Error canceling order: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error canceling order");
        }
    }


}
