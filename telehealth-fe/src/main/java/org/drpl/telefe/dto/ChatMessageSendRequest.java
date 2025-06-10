package org.drpl.telefe.dto;

public class ChatMessageSendRequest {
    private Long senderId;
    private String message;
    private Long prescriptionId;

    public ChatMessageSendRequest(Long senderId, String message, Long prescriptionId) {
        this.senderId = senderId;
        this.message = message;
        this.prescriptionId = prescriptionId;
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

    public Long isHasPrescription() {
        return prescriptionId;
    }

    public void setHasPrescription(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }
}
