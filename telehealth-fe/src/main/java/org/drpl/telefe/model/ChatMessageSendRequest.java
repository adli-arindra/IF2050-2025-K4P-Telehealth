package org.drpl.telefe.model;

public class ChatMessageSendRequest {
    private Long senderId;
    private String message;
    private boolean hasPrescription;

    public ChatMessageSendRequest() {}

    public ChatMessageSendRequest(Long senderId, String message, boolean hasPrescription) {
        this.senderId = senderId;
        this.message = message;
        this.hasPrescription = hasPrescription;
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
}
