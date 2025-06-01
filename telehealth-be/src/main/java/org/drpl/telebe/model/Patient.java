package org.drpl.telebe.model;

import java.util.Date;

public class Patient extends User {

    private MedicalRecord medicalRecord;

    public Patient(String id, String name, String email, String alamat, Date tanggal_lahir) {
        super(id, name, email, alamat, tanggal_lahir);
        this.medicalRecord = new MedicalRecord();
    }

    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(MedicalRecord medicalRecord) {
        this.medicalRecord = medicalRecord;
    }
}