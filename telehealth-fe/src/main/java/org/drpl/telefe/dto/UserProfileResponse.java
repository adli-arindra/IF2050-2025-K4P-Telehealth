package org.drpl.telefe.dto;

import java.time.LocalDate;

public class UserProfileResponse {
    private Long id;
    private String name;
    private String email;
    private String alamat;
    private LocalDate tanggalLahir;

    public UserProfileResponse() {
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAlamat() {
        return this.alamat;
    }

    public LocalDate getTanggalLahir() {
        return this.tanggalLahir;
    }
}
