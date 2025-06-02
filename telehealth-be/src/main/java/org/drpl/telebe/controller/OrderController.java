package org.drpl.telebe.controller;

import org.drpl.telebe.model.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    // Placeholder in-memory list for orders
    private final List<Order> orders = new ArrayList<>();

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        orders.add(order);
        return ResponseEntity.ok("Order created");
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId) {
        return orders.stream()
                .filter(o -> o.getId() != null && o.getId().equals(orderId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable String orderId, @RequestBody Order updatedOrder) {
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getId() != null && order.getId().equals(orderId)) {
                orders.set(i, updatedOrder);
                return ResponseEntity.ok("Order updated");
            }
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{orderId}/finish")
    public ResponseEntity<String> finishOrder(@PathVariable String orderId) {
        for (Order order : orders) {
            if (order.getId() != null && order.getId().equals(orderId)) {
                order.setStatus("finished");
                return ResponseEntity.ok("Order finished");
            }
        }
        return ResponseEntity.notFound().build();
    }
}
