package org.drpl.telebe.controller;

import org.drpl.telebe.dto.ChatMessageSendRequest;
import org.drpl.telebe.dto.ChatMessageResponse;
import org.drpl.telebe.dto.ChatSessionCreateRequest;
import org.drpl.telebe.dto.ChatSessionResponse;
import org.drpl.telebe.model.ChatMessage;
import org.drpl.telebe.model.ChatSession;
import org.drpl.telebe.model.User;
import org.drpl.telebe.repository.ChatMessageRepository;
import org.drpl.telebe.repository.ChatSessionRepository;
import org.drpl.telebe.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatSessionRepository chatSessionRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    public ChatController(ChatSessionRepository chatSessionRepository,
                          ChatMessageRepository chatMessageRepository,
                          UserRepository userRepository) {
        this.chatSessionRepository = chatSessionRepository;
        this.chatMessageRepository = chatMessageRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/session")
    public ResponseEntity<ChatSessionResponse> createChatSession(@RequestBody ChatSessionCreateRequest request) {
        ChatSession newSession = new ChatSession(request.getSessionName());

        Set<User> participants = new HashSet<>();
        if (request.getParticipantIds() != null && !request.getParticipantIds().isEmpty()) {
            for (Long userId : request.getParticipantIds()) {
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: " + userId));
                participants.add(user);
            }
        }
        newSession.setUsers(participants);

        ChatSession savedSession = chatSessionRepository.save(newSession);

        List<Long> participantIds = savedSession.getUsers().stream()
                .map(User::getId)
                .collect(Collectors.toList());
        ChatSessionResponse response = new ChatSessionResponse(
                savedSession.getId(),
                savedSession.getSessionName(),
                savedSession.getCreatedDate(),
                participantIds
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{sessionId}/message")
    public ResponseEntity<String> sendMessage(
            @PathVariable Long sessionId,
            @RequestBody ChatMessageSendRequest request) {

        ChatSession chatSession = chatSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat Session not found with ID: " + sessionId));

        User sender = userRepository.findById(request.getSenderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender User not found with ID: " + request.getSenderId()));

        ChatMessage newMessage = new ChatMessage(
                chatSession,
                sender,
                request.getMessage(),
                null
        );

        chatSession.addMessage(newMessage);
        chatSessionRepository.save(chatSession);

        ChatMessageResponse responseForInternalUse = new ChatMessageResponse(
                newMessage.getId(),
                newMessage.getSender() != null ? newMessage.getSender().getId() : null, // senderId
                newMessage.getMessage(),                                            // message
                newMessage.getPrescription() != null ? newMessage.getPrescription().getId() : null, // prescriptionId (handle null)
                newMessage.getTimestamp()                                           // timestamp
        );

        System.out.println("Message created (internal DTO): " + responseForInternalUse); // For internal logging

        return ResponseEntity.status(HttpStatus.CREATED).body("Message sent");
    }

    @GetMapping("/{sessionId}/messages")
    public ResponseEntity<List<ChatMessageResponse>> getChatMessages(@PathVariable Long sessionId) {
        ChatSession chatSession = chatSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Chat Session not found with ID: " + sessionId));

        List<ChatMessageResponse> messages = chatSession.getMessages().stream()
                .map(msg -> new ChatMessageResponse(
                        msg.getId(),
                        msg.getSender() != null ? msg.getSender().getId() : null,
                        msg.getMessage(),
                        msg.getPrescription() != null ? msg.getPrescription().getId() : null,
                        msg.getTimestamp()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(messages);
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<ChatSessionResponse>> getUserChatSessions() {
        List<ChatSession> sessions = chatSessionRepository.findAll();

        List<ChatSessionResponse> responses = sessions.stream()
                .map(session -> {
                    List<Long> participantIds = session.getUsers().stream()
                            .map(User::getId)
                            .collect(Collectors.toList());
                    return new ChatSessionResponse(
                            session.getId(),
                            session.getSessionName(),
                            session.getCreatedDate(),
                            participantIds
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }
}