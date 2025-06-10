package org.drpl.telefe.dto;

import java.util.Date;

public class PharmacistSignUpRequest {
    private String name;
    private String email;
    private String password;
    private String alamat;
    private Date tanggalLahir;

    public PharmacistSignUpRequest(String name, String email, String password, String alamat, Date tanggalLahir) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.alamat = alamat;
        this.tanggalLahir = tanggalLahir;
    }

    public PharmacistSignUpRequest() {}

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
}