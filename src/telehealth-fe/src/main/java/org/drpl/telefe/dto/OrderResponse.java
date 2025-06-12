package org.drpl.telefe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.drpl.telefe.domain.Prescription;

import java.time.LocalDateTime;

public class OrderResponse {
    private Long id;
    private Long patientId;
    private Long pharmacistId;
    private Prescription prescription;
    private LocalDateTime orderDate;

    @JsonProperty("isPaid")
    private boolean paid;

    private double totalPrice;

    public OrderResponse() {}

    public OrderResponse(Long id, Long patientId, Long pharmacistId, Prescription prescription,
                         LocalDateTime orderDate, boolean paid, double totalPrice) {
        this.id = id;
        this.patientId = patientId;
        this.pharmacistId = pharmacistId;
        this.prescription = prescription;
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

    public Prescription getPrescription() {
        return prescription;
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

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
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
