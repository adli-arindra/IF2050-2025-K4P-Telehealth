package org.drpl.telebe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    @PostMapping
    public ResponseEntity<String> createPrescription(@RequestBody String prescriptionRequest) {
        System.out.println("Create prescription request received: " + prescriptionRequest);
        return ResponseEntity.ok("Prescription created (placeholder)");
    }

    @GetMapping
    public ResponseEntity<String> getAllPrescriptions() {
        System.out.println("Get all prescriptions request received");
        return ResponseEntity.ok("List of prescriptions (placeholder)");
    }

    @GetMapping("/{prescriptionId}")
    public ResponseEntity<String> getPrescriptionById(@PathVariable String prescriptionId) {
        System.out.println("Get prescription by ID " + prescriptionId + " request received");
        return ResponseEntity.ok("Prescription details for " + prescriptionId + " (placeholder)");
    }
}