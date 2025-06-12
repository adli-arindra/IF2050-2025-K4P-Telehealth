package org.drpl.telefe.fetcher;

import org.drpl.telefe.Global;
import org.drpl.telefe.dto.ChatMessageSendRequest;
import org.drpl.telefe.dto.ChatSessionCreateRequest;
import org.drpl.telefe.dto.ChatSessionResponse;
import org.drpl.telefe.dto.ChatMessageResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class ChatFetcher {

    private static final String BASE_URL = "http://localhost:8080/chat"; // Adjust base URL as needed
    private final ObjectMapper objectMapper;

    public ChatFetcher() {
        this.objectMapper = Global.getObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    public ChatSessionResponse createChatSession(ChatSessionCreateRequest request) throws Exception {
        String urlString = BASE_URL + "/session";
        String jsonRequest = objectMapper.writeValueAsString(request);
        String jsonResponse = sendPostRequest(urlString, jsonRequest);
        return objectMapper.readValue(jsonResponse, ChatSessionResponse.class);
    }

    public String sendChatMessage(Long sessionId, ChatMessageSendRequest request) throws Exception {
        String urlString = BASE_URL + "/" + sessionId + "/message";
        String jsonRequest = objectMapper.writeValueAsString(request);
        return sendPostRequest(urlString, jsonRequest);
    }

    public List<ChatMessageResponse> getChatMessages(Long sessionId) throws Exception {
        String urlString = BASE_URL + "/" + sessionId + "/messages";
        String jsonResponse = sendGetRequest(urlString);
        return objectMapper.readValue(jsonResponse, new TypeReference<List<ChatMessageResponse>>() {});
    }

    public List<ChatSessionResponse> getChatSessions() throws Exception {
        String urlString = BASE_URL + "/sessions";
        String jsonResponse = sendGetRequest(urlString);
        return objectMapper.readValue(jsonResponse, new TypeReference<List<ChatSessionResponse>>() {});
    }

    public List<ChatSessionResponse> getChatSessionsByUserId(Long userId) throws Exception {
        String urlString = BASE_URL + "/sessions/" + userId;
        String jsonResponse = sendGetRequest(urlString);
        return objectMapper.readValue(jsonResponse, new TypeReference<List<ChatSessionResponse>>() {});
    }


    private String sendPostRequest(String urlString, String jsonInputString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        try {
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = con.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK && code != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("HTTP POST Request Failed with Error code : " + code);
            }

            try(BufferedReader br = new BufferedReader(
              new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } finally {
            con.disconnect();
        }
    }

    private String sendGetRequest(String urlString) throws Exception {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        try {
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");

            int code = con.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("HTTP GET Request Failed with Error code : " + code);
            }

            try(BufferedReader br = new BufferedReader(
              new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } finally {
            con.disconnect();
        }
    }

    public static void main(String[] args) {
        ChatFetcher chatFetcher = new ChatFetcher();

        try {
            System.out.println("--- Testing Chat Functionality ---");

            // --- 1. Test getChatSessions() ---
            System.out.println("\n1. Fetching all chat sessions...");
            List<ChatSessionResponse> sessions = chatFetcher.getChatSessions();
            if (sessions.isEmpty()) {
                System.out.println("No existing chat sessions found.");
            } else {
                System.out.println("Existing Chat Sessions:");
                for (ChatSessionResponse session : sessions) {
                    System.out.println("  " + session);
                }
            }

            // --- 2. Test createChatSession() ---
            System.out.println("\n2. Creating a new chat session...");
            // Replace 101L and 102L with actual user IDs from your system
            ChatSessionCreateRequest createSessionRequest = new ChatSessionCreateRequest(
                    "Test",
                    Arrays.asList(2L, 1L) // Example participant IDs
            );
            ChatSessionResponse newSession = chatFetcher.createChatSession(createSessionRequest);
            System.out.println("New Chat Session Created: " + newSession);
            Long newSessionId = newSession.getId();
            Long testSenderId = newSession.getParticipantIds().get(0); // Use one of the participants as sender

            // --- 3. Test sendChatMessage() ---
            System.out.println("\n3. Sending a message to the new session (ID: " + newSessionId + ")...");
            ChatMessageSendRequest messageRequest = new ChatMessageSendRequest(
                    testSenderId, // Assuming this user ID exists and is part of the session
                    "Hello, this is a test message from Java client!",
                    null
            );
            String messageSendResponse = chatFetcher.sendChatMessage(newSessionId, messageRequest);
            System.out.println("Message Send Response: " + messageSendResponse);

            // Send another message
            System.out.println("\nSending another message...");
            ChatMessageSendRequest messageRequest2 = new ChatMessageSendRequest(
                    testSenderId,
                    "This is the second test message.",
                    null
            );
            String messageSendResponse2 = chatFetcher.sendChatMessage(newSessionId, messageRequest2);
            System.out.println("Second Message Send Response: " + messageSendResponse2);


            // --- 4. Test getChatMessages() ---
            System.out.println("\n4. Fetching messages for session (ID: " + newSessionId + ")...");
            List<ChatMessageResponse> messages = chatFetcher.getChatMessages(newSessionId);
            if (messages.isEmpty()) {
                System.out.println("No messages found for session ID: " + newSessionId);
            } else {
                System.out.println("Messages in Session " + newSessionId + ":");
                for (ChatMessageResponse message : messages) {
                    System.out.println("  " + message);
                }
            }

            // --- Re-test getChatSessions() to see the new session ---
            System.out.println("\nRe-fetching all chat sessions to confirm new session...");
            List<ChatSessionResponse> updatedSessions = chatFetcher.getChatSessions();
            if (!updatedSessions.isEmpty()) {
                System.out.println("Updated Chat Sessions List:");
                for (ChatSessionResponse session : updatedSessions) {
                    System.out.println("  " + session);
                }
            }


        } catch (IOException e) {
            System.err.println("\nAn IOException occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) { // Catch any other unexpected exceptions
            System.err.println("\nAn unexpected error occurred during testing: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("\n--- Chat Functionality Test Finished ---");
        }
    }
}
