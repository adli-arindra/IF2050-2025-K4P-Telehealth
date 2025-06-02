package org.drpl.telebe.model;

import java.util.Date;
import java.util.List;

public class Prescription {
    private String id;
    private Patient patient;
    private Doctor doctor;
    private List<Medicine> medicines;
    private Date date;
    private String instructions;

    public Prescription() {
    }

    public Prescription(String id, Patient patient, Doctor doctor, List<Medicine> medicines, Date date, String instructions) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.medicines = medicines;
        this.date = date;
        this.instructions = instructions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
