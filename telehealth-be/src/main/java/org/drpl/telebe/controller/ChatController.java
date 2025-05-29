package org.drpl.telebe.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @PostMapping("/session")
    public ResponseEntity<String> createChatSession(@RequestBody String chatSessionRequest) {
        System.out.println("Create chat session request received: " + chatSessionRequest);
        return ResponseEntity.ok("Chat session created (placeholder)");
    }

    @PostMapping("/{sessionId}/message")
    public ResponseEntity<String> sendMessage(
            @PathVariable String sessionId,
            @RequestBody String chatMessageRequest) {
        System.out.println("Send message in session " + sessionId + " request received: " + chatMessageRequest);
        return ResponseEntity.ok("Message sent (placeholder)");
    }

    @GetMapping("/{sessionId}/messages")
    public ResponseEntity<String> getChatMessages(@PathVariable String sessionId) {
        System.out.println("Get messages for session " + sessionId + " request received");
        return ResponseEntity.ok("List of chat messages (placeholder)");
    }

    @GetMapping("/sessions")
    public ResponseEntity<String> getUserChatSessions() {
        System.out.println("Get user chat sessions request received");
        return ResponseEntity.ok("List of user chat sessions (placeholder)");
    }
}