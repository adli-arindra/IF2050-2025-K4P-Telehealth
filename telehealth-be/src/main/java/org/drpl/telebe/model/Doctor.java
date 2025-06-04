package org.drpl.telebe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.drpl.telebe.dto.DoctorSpecializationType;

import java.util.Date;

@Entity
public class Doctor extends User {

    @Enumerated(EnumType.STRING)
    @Column(name = "specialization")
    private DoctorSpecializationType specialization;

    protected Doctor() {
    }

    public Doctor(String name, String email, String hashedPassword, String alamat, Date tanggalLahir,
                  DoctorSpecializationType specialization) {
        super(name, email, hashedPassword, alamat, tanggalLahir);
        this.specialization = specialization;
    }

    public DoctorSpecializationType getSpecialization() {
        return specialization;
    }

    public void setSpecialization(DoctorSpecializationType specialization) {
        this.specialization = specialization;
    }
}