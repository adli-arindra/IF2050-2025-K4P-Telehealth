package org.drpl.telebe.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_message")
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_session_id")
    private ChatSession chatSession;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "has_prescription")
    private boolean hasPrescription;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "prescription_id", referencedColumnName = "id")
    private Prescription prescription;

    @Column(name = "timestamp_message")
    private LocalDateTime timestamp;

    protected ChatMessage() {
        this.timestamp = LocalDateTime.now();
    }

    public ChatMessage(ChatSession chatSession, User sender, String message, boolean hasPrescription, Prescription prescription) {
        this.chatSession = chatSession;
        this.sender = sender;
        this.message = message;
        this.hasPrescription = hasPrescription;
        this.prescription = hasPrescription ? prescription : null;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ChatSession getChatSession() {
        return chatSession;
    }

    public void setChatSession(ChatSession chatSession) {
        this.chatSession = chatSession;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getHasPrescription() {
        return hasPrescription;
    }

    public void setHasPrescription(boolean hasPrescription) {
        this.hasPrescription = hasPrescription;
        if (!hasPrescription) {
            this.prescription = null;
        }
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
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
                "id=" + id +
                ", chatSessionId=" + (chatSession != null ? chatSession.getId() : "null") +
                ", sender=" + (sender != null ? sender.getId() : "null") +
                ", message='" + message + '\'' +
                ", hasPrescription=" + hasPrescription +
                ", prescription=" + (prescription != null ? prescription.getId() : "null") +
                ", timestamp=" + timestamp +
                '}';
    }
}