package org.drpl.telebe.controller;

import org.drpl.telebe.model.Order;
import org.drpl.telebe.repository.OrderRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        orderRepository.save(order);
        return ResponseEntity.ok("Order created");
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable Long orderId, @RequestBody Order updatedOrder) {
        Optional<Order> existingOrderOptional = orderRepository.findById(orderId);

        if (existingOrderOptional.isPresent()) {
            Order existingOrder = existingOrderOptional.get();
            existingOrder.setPatient(updatedOrder.getPatient());
            existingOrder.setPharmacist(updatedOrder.getPharmacist());
            existingOrder.setPrescription(updatedOrder.getPrescription());
            existingOrder.setOrderDate(updatedOrder.getOrderDate());
            existingOrder.setStatus(updatedOrder.getStatus());
            existingOrder.setTotalPrice(updatedOrder.getTotalPrice());

            orderRepository.save(existingOrder);
            return ResponseEntity.ok("Order updated");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{orderId}/finish")
    public ResponseEntity<String> finishOrder(@PathVariable Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus("finished");
            orderRepository.save(order);
            return ResponseEntity.ok("Order finished");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}