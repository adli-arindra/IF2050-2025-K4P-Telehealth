package org.drpl.telefe.model;

import java.time.LocalDateTime;
import java.util.List;

public class Prescription {
    private Long id;
    private Patient patient;
    private Doctor doctor;
    private List<Medicine> medicines;
    private LocalDateTime date;

    public Prescription() {
    }

    public Prescription(Long id, Patient patient, Doctor doctor, List<Medicine> medicines, LocalDateTime date) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.medicines = medicines;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}