package org.drpl.telebe.controller;

import org.drpl.telebe.model.Order;
import org.drpl.telebe.model.Patient;
import org.drpl.telebe.model.Pharmacist;
import org.drpl.telebe.model.Prescription;
import org.drpl.telebe.repository.OrderRepository;
import org.drpl.telebe.repository.PatientRepository;
import org.drpl.telebe.repository.PharmacistRepository;
import org.drpl.telebe.repository.PrescriptionRepository;
import org.drpl.telebe.dto.OrderResponse;
import org.drpl.telebe.dto.OrderRequest;
import org.drpl.telebe.utils.CurrentDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderRepository orderRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PharmacistRepository pharmacistRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient with ID " + request.getPatientId() + " not found."));

        Pharmacist pharmacist = pharmacistRepository.findById(request.getPharmacistId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pharmacist with ID " + request.getPharmacistId() + " not found."));

        Prescription prescription = prescriptionRepository.findById(request.getPrescriptionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prescription with ID " + request.getPrescriptionId() + " not found."));

        Order order = new Order(
                patient,
                pharmacist,
                prescription,
                CurrentDate.get(),
                request.getIsPaid()
        );

        orderRepository.save(order);
        return ResponseEntity.ok("Order created");
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        List<OrderResponse> orderResponses = orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        return orderOptional.map(order -> ResponseEntity.ok(new OrderResponse(order)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateOrder(@PathVariable Long orderId, @RequestBody OrderRequest request) {
        Optional<Order> existingOrderOptional = orderRepository.findById(orderId);

        if (existingOrderOptional.isPresent()) {
            Order existingOrder = existingOrderOptional.get();

            Patient patient = patientRepository.findById(request.getPatientId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient with ID " + request.getPatientId() + " not found."));

            Pharmacist pharmacist = pharmacistRepository.findById(request.getPharmacistId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pharmacist with ID " + request.getPharmacistId() + " not found."));

            Prescription prescription = prescriptionRepository.findById(request.getPrescriptionId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prescription with ID " + request.getPrescriptionId() + " not found."));

            existingOrder.setPatient(patient);
            existingOrder.setPharmacist(pharmacist);
            existingOrder.setPrescription(prescription);
            existingOrder.setOrderDate(CurrentDate.get());
            existingOrder.setPaid(request.getIsPaid());
            existingOrder.calculateTotalPrice();

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
            order.setPaid(true);
            orderRepository.save(order);
            return ResponseEntity.ok("Order finished");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderRepository.findByPatientIdOrPharmacistId(userId, userId);
        List<OrderResponse> orderResponses = orders.stream()
                .map(OrderResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderResponses);
    }
}