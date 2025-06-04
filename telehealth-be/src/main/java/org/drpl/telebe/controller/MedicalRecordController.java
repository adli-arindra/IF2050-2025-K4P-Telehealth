package org.drpl.telebe.controller;

import org.drpl.telebe.model.MedicalRecord;
import org.drpl.telebe.model.Patient;
import org.drpl.telebe.repository.MedicalRecordRepository;
import org.drpl.telebe.repository.PatientRepository;
import org.drpl.telebe.dto.MedicalRecordRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/medical-records")
public class MedicalRecordController {

    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/{patientId}")
    public ResponseEntity<MedicalRecord> getMedicalRecordByPatientId(@PathVariable Long patientId) {
        Optional<MedicalRecord> medicalRecord = medicalRecordRepository.findById(patientId);

        return medicalRecord.map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medical record for patient ID " + patientId + " not found."));
    }

    @PostMapping("/{patientId}")
    public ResponseEntity<MedicalRecord> saveOrUpdateMedicalRecord(
            @PathVariable Long patientId,
            @RequestBody MedicalRecordRequest request) {

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient with ID " + patientId + " not found."));

        Optional<MedicalRecord> existingRecord = medicalRecordRepository.findById(patientId);

        MedicalRecord medicalRecord;
        HttpStatus status;

        if (existingRecord.isPresent()) {
            medicalRecord = existingRecord.get();
            medicalRecord.setDiagnosis(request.getDiagnosis());
            medicalRecord.setTreatment(request.getTreatment());
            medicalRecord.setRecordDate(request.getRecordDate());
            status = HttpStatus.OK;
        } else {
            medicalRecord = new MedicalRecord(patient, request.getDiagnosis(), request.getTreatment(), request.getRecordDate());
            status = HttpStatus.CREATED;
        }

        MedicalRecord savedMedicalRecord = medicalRecordRepository.save(medicalRecord);

        return ResponseEntity.status(status).body(savedMedicalRecord);
    }
}