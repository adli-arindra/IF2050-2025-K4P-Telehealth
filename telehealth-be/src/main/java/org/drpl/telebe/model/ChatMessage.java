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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "prescription_id", referencedColumnName = "id")
    private Prescription prescription;

    @Column(name = "timestamp_message")
    private LocalDateTime timestamp;

    protected ChatMessage() {
        this.timestamp = LocalDateTime.now();
    }

    public ChatMessage(ChatSession chatSession, User sender, String message, Prescription prescription) {
        this.chatSession = chatSession;
        this.sender = sender;
        this.message = message;
        this.prescription = prescription; // Directly assign, null if no prescription
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

    public Prescription getPrescription() {
        return prescription;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
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
                ", prescription=" + (prescription != null ? prescription.getId() : "null") +
                ", timestamp=" + timestamp +
                '}';
    }
}