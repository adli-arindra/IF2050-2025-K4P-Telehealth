package org.drpl.telebe.dto;

import java.time.LocalDateTime;

public class ChatMessageResponse {
    private Long id;
    private Long senderId;
    private String message;
    private boolean hasPrescription;
    private Long prescriptionId;
    private LocalDateTime timestamp;

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

    public Long getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }

    public boolean getHasPrescription() {
        return hasPrescription;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}