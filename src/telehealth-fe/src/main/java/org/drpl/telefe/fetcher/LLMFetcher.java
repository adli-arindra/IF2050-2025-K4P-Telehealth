package org.drpl.telefe.fetcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.drpl.telefe.Global;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LLMFetcher {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl;

    public LLMFetcher() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = Global.getObjectMapper();
        this.baseUrl = Global.BASE_URL;
    }

    public String sendQuery(String query, Long patientId) throws IOException, InterruptedException {
        ObjectNode requestBodyJson = objectMapper.createObjectNode();
        requestBodyJson.put("query", query);
        requestBodyJson.put("patientId", patientId);
        String requestBody = requestBodyJson.toString();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/llm"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new IOException("Failed to fetch LLM response. Status code: " + response.statusCode() + ", Body: " + response.body());
        }
    }

    public static void main(String[] args) {
        LLMFetcher fetcher = new LLMFetcher();

        try {
            String llmResponse = fetcher.sendQuery("Hello", 1L);
            System.out.println("LLM Response: " + llmResponse);
        } catch (IOException | InterruptedException e) {
            System.err.println("Error sending query: " + e.getMessage());
            e.printStackTrace();
        }
    }
}