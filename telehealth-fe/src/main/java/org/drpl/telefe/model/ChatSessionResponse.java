package org.drpl.telefe.model;

import java.time.LocalDateTime;
import java.util.List;

public class ChatSessionResponse {
    private Long id;
    private String sessionName;
    private LocalDateTime createdDate;
    private List<Long> participantIds;

    public ChatSessionResponse() {}

    public ChatSessionResponse(Long id, String sessionName, LocalDateTime createdDate, List<Long> participantIds) {
        this.id = id;
        this.sessionName = sessionName;
        this.createdDate = createdDate;
        this.participantIds = participantIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<Long> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
    }
}
