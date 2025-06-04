package org.drpl.telebe.dto;

import java.time.LocalDateTime;
import java.util.List;

public class ChatSessionResponse {
    private Long id;
    private String sessionName;
    private LocalDateTime createdDate;
    private List<Long> participantIds;

    public ChatSessionResponse(Long id, String sessionName, LocalDateTime createdDate, List<Long> participantIds) {
        this.id = id;
        this.sessionName = sessionName;
        this.createdDate = createdDate;
        this.participantIds = participantIds;
    }

    public Long getId() {
        return id;
    }

    public String getSessionName() {
        return sessionName;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public List<Long> getParticipantIds() {
        return participantIds;
    }
}