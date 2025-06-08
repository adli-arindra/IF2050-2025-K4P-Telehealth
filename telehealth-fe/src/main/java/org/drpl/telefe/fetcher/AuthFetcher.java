package org.drpl.telefe.fetcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.drpl.telefe.model.User;
import org.drpl.telefe.dto.AuthRequest;
import org.drpl.telefe.dto.UserProfileResponse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class AuthFetcher {

    private static final String BASE_URL = "http://localhost:8080/auth";
    private final ObjectMapper objectMapper = new ObjectMapper();

    public User login(String email, String password) throws IOException {
        AuthRequest request = new AuthRequest(email, password);
        String jsonRequest = objectMapper.writeValueAsString(request);

        HttpURLConnection conn = createConnection(BASE_URL + "/login", "POST");
        writeRequestBody(conn, jsonRequest);

        int status = conn.getResponseCode();
        if (status == 200) {
            return objectMapper.readValue(conn.getInputStream(), User.class);
        } else {
            throw new IOException("Login failed with status: " + status + " - " + readError(conn));
        }
    }

    public UserProfileResponse register(String endpoint, Object signUpRequest) throws IOException {
        String jsonRequest = objectMapper.writeValueAsString(signUpRequest);

        HttpURLConnection conn = createConnection(BASE_URL + endpoint, "POST");
        writeRequestBody(conn, jsonRequest);

        int status = conn.getResponseCode();
        if (status == 201) {
            return objectMapper.readValue(conn.getInputStream(), UserProfileResponse.class);
        } else {
            throw new IOException("Registration failed with status: " + status + " - " + readError(conn));
        }
    }

    private HttpURLConnection createConnection(String urlStr, String method) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        return conn;
    }

    private void writeRequestBody(HttpURLConnection conn, String json) throws IOException {
        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes());
            os.flush();
        }
    }

    private String readError(HttpURLConnection conn) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
            return br.readLine();
        }
    }
}