package org.drpl.telebe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @GetMapping
    public ResponseEntity<String> getAllDoctors() {
        System.out.println("Get all doctors request received");
        return ResponseEntity.ok("List of doctors (placeholder)");
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<String> getDoctorById(@PathVariable String doctorId) {
        System.out.println("Get doctor by ID " + doctorId + " request received");
        return ResponseEntity.ok("Doctor details for " + doctorId + " (placeholder)");
    }

    @PostMapping
    public ResponseEntity<String> createDoctor(@RequestBody String doctorRequest) {
        System.out.println("Create doctor request received: " + doctorRequest);
        return ResponseEntity.ok("Doctor created (placeholder)");
    }
}