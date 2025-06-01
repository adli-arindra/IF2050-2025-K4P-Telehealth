package org.drpl.telebe.model;

public class Doctor extends User {
    private String specialization;
    private String license_number;

    public Doctor() {
        super();
    }

    public Doctor(String id, String name, String email, String alamat, Date tanggal_lahir, String specialization, String license_number) {
        super(id, name, email, alamat, tanggal_lahir);
        this.specialization = specialization;
        this.license_number = license_number;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getLicense_number() {
        return license_number;
    }

    public void setLicense_number(String license_number) {
        this.license_number = license_number;
    }
}

