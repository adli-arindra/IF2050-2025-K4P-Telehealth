package org.drpl.telebe.controller;

import org.drpl.telebe.dto.PrescriptionRequest;
import org.drpl.telebe.dto.PrescriptionResponse;
import org.drpl.telebe.model.Prescription;
import org.drpl.telebe.model.Patient;
import org.drpl.telebe.model.Doctor;
import org.drpl.telebe.model.Medicine;
import org.drpl.telebe.repository.PrescriptionRepository;
import org.drpl.telebe.repository.PatientRepository;
import org.drpl.telebe.repository.DoctorRepository;
import org.drpl.telebe.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @GetMapping
    public List<PrescriptionResponse> getAllPrescriptions() {
        List<Prescription> prescriptions = prescriptionRepository.findAll();

        return prescriptions.stream()
                .map(prescription -> new PrescriptionResponse(
                        prescription.getId(),
                        prescription.getPatient() != null ? prescription.getPatient().getId() : null,
                        prescription.getDoctor() != null ? prescription.getDoctor().getId() : null,
                        prescription.getMedicines(),
                        prescription.getDate()
                ))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<String> createPrescription(@RequestBody PrescriptionRequest request) {
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient with ID " + request.getPatientId() + " not found."));

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Doctor with ID " + request.getDoctorId() + " not found."));

        List<Medicine> medicinesToPersist = request.getMedicines();
        if (medicinesToPersist != null && !medicinesToPersist.isEmpty()) {
            medicineRepository.saveAll(medicinesToPersist);
        }

        Prescription prescription = new Prescription(
                patient,
                doctor,
                medicinesToPersist,
                LocalDateTime.now()
        );

        Prescription savedPrescription = prescriptionRepository.save(prescription);
        return ResponseEntity.status(HttpStatus.CREATED).body("Success");
    }

    @GetMapping("/{userId}")
    public List<PrescriptionResponse> getPrescriptionsByUserId(@PathVariable Long userId) { // Changed return type
        List<Prescription> prescriptions = prescriptionRepository.findByPatientIdOrDoctorId(userId, userId);

        return prescriptions.stream()
                .map(prescription -> new PrescriptionResponse(
                        prescription.getId(),
                        prescription.getPatient() != null ? prescription.getPatient().getId() : null,
                        prescription.getDoctor() != null ? prescription.getDoctor().getId() : null,
                        prescription.getMedicines(),
                        prescription.getDate()
                ))
                .collect(Collectors.toList());
    }
}