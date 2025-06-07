package org.drpl.telefe.fetcher;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.drpl.telefe.model.PrescriptionRequest;
import org.drpl.telefe.model.PrescriptionResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class PrescriptionFetcher {

    private static final String BASE_URL = "http://localhost:8080/prescriptions";
    private final ObjectMapper objectMapper;

    public PrescriptionFetcher() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    public List<PrescriptionResponse> getAllPrescriptions() throws Exception {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String response = br.lines().collect(Collectors.joining());
        conn.disconnect();

        return objectMapper.readValue(response, new TypeReference<List<PrescriptionResponse>>() {});
    }

    public List<PrescriptionResponse> getPrescriptionsByUserId(Long userId) throws Exception {
        URL url = new URL(BASE_URL + "/" + userId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String response = br.lines().collect(Collectors.joining());
        conn.disconnect();

        return objectMapper.readValue(response, new TypeReference<List<PrescriptionResponse>>() {});
    }

    public String createPrescription(PrescriptionRequest request) throws Exception {
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");

        String jsonInputString = objectMapper.writeValueAsString(request);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        if (conn.getResponseCode() != 201) {
            throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
        String response = br.lines().collect(Collectors.joining());
        conn.disconnect();

        return response;
    }
}
