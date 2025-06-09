package org.drpl.telefe.fetcher;

import org.drpl.telefe.dto.ChatMessageSendRequest;
import org.drpl.telefe.dto.ChatSessionCreateRequest;
import org.drpl.telefe.dto.ChatSessionResponse;
import org.drpl.telefe.dto.ChatMessageResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

public class ChatFetcher {

    private static final String BASE_URL = "http://localhost:8080/chat"; // Adjust base URL as needed
    private final ObjectMapper objectMapper;

    public ChatFetcher() {
        this.objectMapper = new ObjectMapper();
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
}
