package org.drpl.telebe.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order {
    private String id;
    private Patient patient;
    private Pharmacist pharmacist;
    private List<Medicine> medicines;
    private Date orderDate;
    private String status;
    private BigDecimal totalPrice;

    public Order() {
    }

    public Order(String id, Patient patient, Pharmacist pharmacist, List<Medicine> medicines, Date orderDate, String status, BigDecimal totalPrice) {
        this.id = id;
        this.patient = patient;
        this.pharmacist = pharmacist;
        this.medicines = medicines;
        this.orderDate = orderDate;
        this.status = status;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Pharmacist getPharmacist() {
        return pharmacist;
    }

    public void setPharmacist(Pharmacist pharmacist) {
        this.pharmacist = pharmacist;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
