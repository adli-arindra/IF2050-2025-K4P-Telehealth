package org.drpl.telebe.model;

import java.time.LocalDateTime;

public class ChatMessage {
    private String sender;         // Siapa yang mengirim (Pasien atau Dokter)
    private String message;        // Isi pesan
    private boolean hasPrescription; // Apakah dalam pesan ini ada resep?
    private String prescription;   // Isi resep (jika ada)
    private LocalDateTime timestamp; // Waktu pesan dikirim

    public ChatMessage(String sender, String message, boolean hasPrescription, String prescription) {
        this.sender = sender;
        this.message = message;
        this.hasPrescription = hasPrescription;
        this.prescription = hasPrescription ? prescription : null;
        this.timestamp = LocalDateTime.now(); // waktu saat pesan dibuat
    }

    // Getter & Setter
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean hasPrescription() {
        return hasPrescription;
    }

    public void setHasPrescription(boolean hasPrescription) {
        this.hasPrescription = hasPrescription;
        if (!hasPrescription) {
            this.prescription = null;
        }
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        if (this.hasPrescription) {
            this.prescription = prescription;
        }
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "sender='" + sender + '\'' +
                ", message='" + message + '\'' +
                ", hasPrescription=" + hasPrescription +
                ", prescription='" + prescription + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
