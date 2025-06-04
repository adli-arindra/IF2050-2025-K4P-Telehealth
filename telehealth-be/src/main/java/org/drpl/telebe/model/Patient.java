package org.drpl.telebe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "patient")
@PrimaryKeyJoinColumn(name = "id")
public class Patient extends User {

    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL)
    @JsonBackReference
    private MedicalRecord medicalRecord;

    protected Patient() {
    }

    public Patient(String name, String email, String hashedPassword, String alamat, Date tanggalLahir) {
        super(name, email, hashedPassword, alamat, tanggalLahir);
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
}