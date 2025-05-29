package org.drpl.telebe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody String orderCreateRequest) {
        System.out.println("Create order request received: " + orderCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Order created successfully (placeholder)");
    }

    @GetMapping
    public ResponseEntity<String> getAllOrders() {
        System.out.println("Get all orders request received");
        return ResponseEntity.ok("List of all orders (placeholder)");
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<String> getOrderById(@PathVariable String orderId) {
        System.out.println("Get order by ID " + orderId + " request received");
        return ResponseEntity.ok("Details for order ID: " + orderId + " (placeholder)");
    }

    @PutMapping("/{orderId}/finish")
    public ResponseEntity<String> finishOrder(@PathVariable String orderId) {
        System.out.println("Finish order request received for order ID: " + orderId);
        return ResponseEntity.ok("Order ID: " + orderId + " finished successfully (placeholder)");
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateOrder(
            @PathVariable String orderId,
            @RequestBody String orderUpdateRequest) {
        System.out.println("Update order request received for ID: " + orderId + ", data: " + orderUpdateRequest);
        return ResponseEntity.ok("Order ID: " + orderId + " updated (placeholder)");
    }
}