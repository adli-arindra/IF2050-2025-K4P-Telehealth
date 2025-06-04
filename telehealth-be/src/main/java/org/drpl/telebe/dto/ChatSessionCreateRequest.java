package org.drpl.telebe.dto;

import java.util.List;

public class ChatSessionCreateRequest {
    private String sessionName;
    private List<Long> participantIds;

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public List<Long> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
    }
}