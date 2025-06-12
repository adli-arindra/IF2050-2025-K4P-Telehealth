package org.drpl.telebe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "telehealth_user")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    @Column(name = "hashed_password")
    private String hashedPassword;

    private String alamat;

    @Column(name = "tanggal_lahir")
    @Temporal(TemporalType.DATE)
    private Date tanggalLahir;

    protected User() {
    }

    public User(String name, String email, String hashedPassword, String alamat, Date tanggalLahir) {
        this.name = name;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.alamat = alamat;
        this.tanggalLahir = tanggalLahir;
    }

    public void updateProfile(String name, String email, String alamat, Date tanggalLahir) {
        this.name = name;
        this.email = email;
        this.alamat = alamat;
        this.tanggalLahir = tanggalLahir;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public UserType getUserType() {
        if (this instanceof Patient) return UserType.PATIENT;
        if (this instanceof Doctor) return UserType.DOCTOR;
        if (this instanceof Pharmacist) return UserType.PHARMACIST;
        throw new IllegalStateException("Unknown user type");
    }

}