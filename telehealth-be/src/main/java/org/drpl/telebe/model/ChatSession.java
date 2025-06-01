package org.drpl.telebe.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChatSession {
    private String sessionId;
    private final List<ChatMessage> messages;
    private Set<String> users;

    public ChatSession(String sessionId) {
        this.sessionId = sessionId;
        this.messages = new ArrayList<>();
        this.users = new HashSet<>();
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void addMessage(ChatMessage newMessage) {
        this.messages.add(newMessage);
        this.users.add(newMessage.getSender());
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }

    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.findAndRegisterModules();
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
