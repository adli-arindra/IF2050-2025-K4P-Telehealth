package org.drpl.telefe.dto;

public class OrderRequest {
    private Long patientId;
    private Long pharmacistId;
    private Long prescriptionId;
    private Boolean isPaid;

    public OrderRequest() {}

    public OrderRequest(Long patientId, Long pharmacistId, Long prescriptionId, Boolean isPaid) {
        this.patientId = patientId;
        this.pharmacistId = pharmacistId;
        this.prescriptionId = prescriptionId;
        this.isPaid = isPaid;
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

    public Boolean getIsPaid() {
        return isPaid;
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

    public void setIsPaid(Boolean paid) {
        isPaid = paid;
    }
}