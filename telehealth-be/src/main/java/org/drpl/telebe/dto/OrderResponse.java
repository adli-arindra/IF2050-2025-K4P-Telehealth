package org.drpl.telebe.dto;

import org.drpl.telebe.model.Order;
import java.math.BigDecimal;
import java.util.Date;

public class OrderResponse {
    private Long id;
    private Long patientId;
    private Long pharmacistId;
    private PrescriptionResponse prescription;
    private Date orderDate;
    private boolean isPaid; // Changed to boolean
    private BigDecimal totalPrice;

    public OrderResponse(Order order) {
        this.id = order.getId();
        this.patientId = order.getPatient() != null ? order.getPatient().getId() : null;
        this.pharmacistId = order.getPharmacist() != null ? order.getPharmacist().getId() : null;

        if (order.getPrescription() != null) {
            this.prescription = new PrescriptionResponse(
                    order.getPrescription().getId(),
                    order.getPrescription().getPatient() != null ? order.getPrescription().getPatient().getId() : null,
                    order.getPrescription().getDoctor() != null ? order.getPrescription().getDoctor().getId() : null,
                    order.getPrescription().getMedicines(),
                    order.getPrescription().getDate()
            );
        } else {
            this.prescription = null;
        }
        this.orderDate = order.getOrderDate();
        this.isPaid = order.isPaid(); // Changed getter
        this.totalPrice = order.getTotalPrice();
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

    public PrescriptionResponse getPrescription() {
        return prescription;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public boolean getIsPaid() { // Standard getter for boolean is 'isPaid()' but for JSON serialization 'getIsPaid()' is often used
        return isPaid;
    }

    public BigDecimal getTotalPrice() {
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

    public void setPrescription(PrescriptionResponse prescription) {
        this.prescription = prescription;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public void setIsPaid(boolean isPaid) { // Standard setter for boolean is 'setPaid()'
        this.isPaid = isPaid;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}