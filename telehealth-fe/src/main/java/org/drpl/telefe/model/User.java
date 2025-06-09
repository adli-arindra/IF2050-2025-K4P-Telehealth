package org.drpl.telefe.model;

import java.util.Date;

public abstract class User {
    private Long id;
    private String name;
    private String email;
    private String alamat;
    private Date tanggalLahir;

    public User() {}

    public User(Long id, String name, String email, String alamat, Date tanggalLahir) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.alamat = alamat;
        this.tanggalLahir = tanggalLahir;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public Date getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(Date tanggalLahir) { this.tanggalLahir = tanggalLahir; }
}