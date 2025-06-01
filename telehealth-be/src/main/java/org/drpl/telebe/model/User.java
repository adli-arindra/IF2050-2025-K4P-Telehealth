package org.drpl.telebe.model;

import java.util.Date;

public abstract class User {

    private String id;
    private String name;
    private String email;
    private String alamat;
    private Date tanggalLahir;

    public User(String id, String name, String email, String alamat, Date tanggalLahir) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.alamat = alamat;
        this.tanggalLahir = tanggalLahir;
    }

    public void updateProfile(String name, String email, String alamat, Date tanggalLahir) {
        this.name = name;
        this.email = email;
        this.alamat = alamat;
        this.tanggalLahir = tanggalLahir;
    }

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Date getTanggal_lahir() {
        return tanggalLahir;
    }

    public void setTanggal_lahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }
}