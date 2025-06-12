package org.drpl.telefe.dto;

import java.time.LocalDateTime;

public class ChatMessageResponse {
    private Long id;
    private Long senderId;
    private String message;
    private boolean hasPrescription;
    private Long prescriptionId;
    private LocalDateTime timestamp;

    public ChatMessageResponse() {}

    public ChatMessageResponse(Long id, Long senderId, String message, boolean hasPrescription, Long prescriptionId, LocalDateTime timestamp) {
        this.id = id;
        this.senderId = senderId;
        this.message = message;
        this.hasPrescription = hasPrescription;
        this.prescriptionId = prescriptionId;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isHasPrescription() {
        return hasPrescription;
    }

    public void setHasPrescription(boolean hasPrescription) {
        this.hasPrescription = hasPrescription;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
