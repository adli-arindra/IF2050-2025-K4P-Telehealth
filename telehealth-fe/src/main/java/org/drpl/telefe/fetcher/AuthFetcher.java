package org.drpl.telefe.fetcher;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.drpl.telefe.dto.*;
import org.drpl.telefe.model.User;
import org.drpl.telefe.Global;
import org.drpl.telefe.utils.HttpUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.Date;
import java.util.List;

public class AuthFetcher {

    private static final String BASE_URL = Global.BASE_URL + "/auth";
    private final ObjectMapper objectMapper = Global.getObjectMapper();

    public User login(String email, String password) throws IOException {
        AuthRequest request = new AuthRequest(email, password);
        String jsonRequest = objectMapper.writeValueAsString(request);

        HttpURLConnection conn = HttpUtils.createConnection(BASE_URL + "/login", "POST");
        HttpUtils.writeRequestBody(conn, jsonRequest);

        int status = conn.getResponseCode();
        if (status == 200) {
            return objectMapper.readValue(HttpUtils.readResponse(conn), User.class);
        } else {
            throw new IOException("Login failed with status: " + status + " - " + HttpUtils.readError(conn));
        }
    }

    public User register(DoctorSignUpRequest signUpRequest) throws IOException {
        String jsonRequest = objectMapper.writeValueAsString(signUpRequest);

        HttpURLConnection conn = HttpUtils.createConnection(BASE_URL + "/register/doctor", "POST");
        HttpUtils.writeRequestBody(conn, jsonRequest);

        int status = conn.getResponseCode();
        if (status == 201) {
            return objectMapper.readValue(HttpUtils.readResponse(conn), User.class);
        } else {
            throw new IOException("Registration failed with status: " + status + " - " + HttpUtils.readError(conn));
        }
    }

    public User register(PatientSignUpRequest signUpRequest) throws IOException {
        String jsonRequest = objectMapper.writeValueAsString(signUpRequest);

        HttpURLConnection conn = HttpUtils.createConnection(BASE_URL + "/register/patient", "POST");
        HttpUtils.writeRequestBody(conn, jsonRequest);

        int status = conn.getResponseCode();
        if (status == 201) {
            return objectMapper.readValue(HttpUtils.readResponse(conn), User.class);
        } else {
            throw new IOException("Registration failed with status: " + status + " - " + HttpUtils.readError(conn));
        }
    }

    public User register(PharmacistSignUpRequest signUpRequest) throws IOException {
        String jsonRequest = objectMapper.writeValueAsString(signUpRequest);

        HttpURLConnection conn = HttpUtils.createConnection(BASE_URL + "/register/pharmacist", "POST");
        HttpUtils.writeRequestBody(conn, jsonRequest);

        int status = conn.getResponseCode();
        if (status == 201) {
            return objectMapper.readValue(HttpUtils.readResponse(conn), User.class);
        } else {
            throw new IOException("Registration failed with status: " + status + " - " + HttpUtils.readError(conn));
        }
    }

    public List<User> getUsers() throws IOException {
        HttpURLConnection conn = HttpUtils.createConnection(BASE_URL + "/users", "GET");

        int status = conn.getResponseCode();
        if (status == 200) {
            String jsonResponse = HttpUtils.readResponse(conn);
            return objectMapper.readValue(jsonResponse, new TypeReference<List<User>>() {});
        } else {
            throw new IOException("Failed to fetch users with status: " + status + " - " + HttpUtils.readError(conn));
        }
    }

    public User getUserById(long id) throws IOException {
        String url = BASE_URL + "/profile/" + id;
        HttpURLConnection conn = HttpUtils.createConnection(url, "GET");

        int status = conn.getResponseCode();
        if (status == 200) {
            String jsonResponse = HttpUtils.readResponse(conn);
            return objectMapper.readValue(jsonResponse, User.class);
        } else {
            throw new IOException("Failed to fetch user by ID " + id + " with status: " + status + " - " + HttpUtils.readError(conn));
        }
    }

    public static void main(String[] args) {
        AuthFetcher fetcher = new AuthFetcher();

        try {
            long userIdToFetch = 1L;
            User fetchedUser = fetcher.getUserById(userIdToFetch);
            System.out.println("Fetched User by ID " + userIdToFetch + ": " + fetchedUser);

            List<User> users = fetcher.getUsers();
            System.out.println("\nAll Users:");
            for (User user : users) {
                System.out.println(" - " + user);
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}