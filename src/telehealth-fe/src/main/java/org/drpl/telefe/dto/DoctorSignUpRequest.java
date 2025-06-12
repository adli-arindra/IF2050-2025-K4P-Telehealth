package org.drpl.telefe.dto;

import org.drpl.telefe.domain.DoctorSpecializationType;

import java.util.Date;

public class DoctorSignUpRequest {
    private String name;
    private String email;
    private String password;
    private String alamat;
    private Date tanggalLahir;
    private DoctorSpecializationType specializationType;

    public DoctorSignUpRequest(String name, String email, String password, String alamat, Date tanggalLahir, DoctorSpecializationType specializationType) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.alamat = alamat;
        this.tanggalLahir = tanggalLahir;
        this.specializationType = specializationType;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public Date getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(Date tanggalLahir) { this.tanggalLahir = tanggalLahir; }

    public DoctorSpecializationType getSpecialization() { return specializationType; }
    public void setSpecialization(DoctorSpecializationType specializationType) { this.specializationType = specializationType; }
}