package org.drpl.telefe.domain;

import java.util.Date;

public class Patient extends User {
    private MedicalRecord medicalRecord;

    public Patient() {}

    public Patient(Long id, String name, String email, String alamat, Date tanggalLahir) {
        super(id, name, email, alamat, tanggalLahir);
    }

    public MedicalRecord getMedicalRecord() { return this.medicalRecord; }
    public void setMedicalRecord(MedicalRecord medicalRecord) { this.medicalRecord = medicalRecord; }
}
