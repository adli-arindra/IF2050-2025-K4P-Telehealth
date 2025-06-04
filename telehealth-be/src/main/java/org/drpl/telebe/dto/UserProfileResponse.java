package org.drpl.telebe.dto;

import java.util.Date;

public class UserProfileResponse {
    private Long id;
    private String name;
    private String email;
    private String alamat;
    private Date tanggalLahir;

    public UserProfileResponse(Long id, String name, String email, String alamat, Date tanggalLahir) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.alamat = alamat;
        this.tanggalLahir = tanggalLahir;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAlamat() {
        return alamat;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }
}