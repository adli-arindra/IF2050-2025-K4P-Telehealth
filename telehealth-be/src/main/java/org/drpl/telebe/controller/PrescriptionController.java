package org.drpl.telebe.controller;

import org.drpl.telebe.model.Prescription;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
public class PrescriptionController {

    // Placeholder in-memory list for prescriptions
    private final List<Prescription> prescriptions = new ArrayList<>();

    @PostMapping
    public ResponseEntity<String> createPrescription(@RequestBody Prescription prescription) {
        prescriptions.add(prescription);
        return ResponseEntity.ok("Prescription created");
    }

    @GetMapping
    public ResponseEntity<List<Prescription>> getAllPrescriptions() {
        return ResponseEntity.ok(prescriptions);
    }

    @GetMapping("/{prescriptionId}")
    public ResponseEntity<Prescription> getPrescriptionById(@PathVariable String prescriptionId) {
        return prescriptions.stream()
                .filter(p -> p.getId() != null && p.getId().equals(prescriptionId))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
