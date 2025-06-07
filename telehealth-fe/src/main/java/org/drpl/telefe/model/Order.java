package org.drpl.telefe.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Order {
    private Long id;
    private Long patientId;
    private Long pharmacistId;
    private Prescription prescription;
    private LocalDateTime orderDate;
    private boolean isPaid;
    private BigDecimal totalPrice;

    public Order(Long id, Long patientId, Long pharmacistId, Prescription prescription, LocalDateTime orderDate, boolean isPaid, BigDecimal totalPrice) {
        this.id = id;
        this.patientId = patientId;
        this.pharmacistId = pharmacistId;
        this.prescription = prescription;
        this.orderDate = orderDate;
        this.isPaid = isPaid;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getPharmacistId() {
        return pharmacistId;
    }

    public void setPharmacistId(Long pharmacistId) {
        this.pharmacistId = pharmacistId;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}