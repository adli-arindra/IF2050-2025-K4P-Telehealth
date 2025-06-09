package org.drpl.telefe.model;

import java.time.LocalDate;
import java.util.Date;

public class Doctor extends User {
    private DoctorSpecializationType specialization;

    public Doctor() {}

    public Doctor(Long id, String name, String email, String alamat, Date tanggalLahir) {
        super(id, name, email, alamat, tanggalLahir);
    }

    public DoctorSpecializationType getSpecialization() { return specialization; }
    public void setSpecialization(DoctorSpecializationType specialization) { this.specialization = specialization; }
}
