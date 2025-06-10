package org.drpl.telefe.dto;

import java.time.LocalDateTime;

public class OrderResponse {
    private Long id;
    private Long patientId;
    private Long pharmacistId;
    private Long prescriptionId;
    private LocalDateTime orderDate;
    private boolean paid;
    private double totalPrice; // From calculateTotalPrice()

    public OrderResponse() {}

    public OrderResponse(Long id, Long patientId, Long pharmacistId, Long prescriptionId, LocalDateTime orderDate, boolean paid, double totalPrice) {
        this.id = id;
        this.patientId = patientId;
        this.pharmacistId = pharmacistId;
        this.prescriptionId = prescriptionId;
        this.orderDate = orderDate;
        this.paid = paid;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public Long getPharmacistId() {
        return pharmacistId;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public boolean isPaid() {
        return paid;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setPharmacistId(Long pharmacistId) {
        this.pharmacistId = pharmacistId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}