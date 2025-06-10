package org.drpl.telefe.fetcher;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
// import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule; // No longer needed here
import org.drpl.telefe.Global;
import org.drpl.telefe.dto.PrescriptionRequest;
import org.drpl.telefe.dto.PrescriptionResponse;
import org.drpl.telefe.domain.Medicine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PrescriptionFetcher {

    private static final String BASE_URL = "http://localhost:8080/prescriptions";
    private final ObjectMapper objectMapper;

    public PrescriptionFetcher() {
        // Get the globally configured ObjectMapper instance
        this.objectMapper = Global.getObjectMapper();
        // REMOVE THIS LINE: You already configured it in Global.java
        // this.objectMapper.registerModule(new JavaTimeModule());
    }

    public List<PrescriptionResponse> getAllPrescriptions() throws IOException { // Changed Exception to IOException
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setConnectTimeout(5000); // Add timeouts
        conn.setReadTimeout(5000);    // Add timeouts


        String response = readResponse(conn);
        checkResponse(conn);
        conn.disconnect(); // Ensure connection is disconnected after reading and checking

        return objectMapper.readValue(response, new TypeReference<List<PrescriptionResponse>>() {});
    }

    public List<PrescriptionResponse> getPrescriptionsByUserId(Long userId) throws IOException { // Changed Exception to IOException
        URL url = new URL(BASE_URL + "/user/" + userId); // Assumed /user/
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        conn.setConnectTimeout(5000); // Add timeouts
        conn.setReadTimeout(5000);    // Add timeouts

        String response = readResponse(conn);
        checkResponse(conn);
        conn.disconnect(); // Ensure connection is disconnected after reading and checking

        return objectMapper.readValue(response, new TypeReference<List<PrescriptionResponse>>() {});
    }

    public String createPrescription(PrescriptionRequest request) throws IOException { // Changed Exception to IOException
        URL url = new URL(BASE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setConnectTimeout(5000); // Add timeouts
        conn.setReadTimeout(5000);    // Add timeouts

        String jsonInputString = objectMapper.writeValueAsString(request);
        System.out.println("Sending JSON: " + jsonInputString); // Added for debugging

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        String response = readResponse(conn); // Read response from input stream
        // Check response code *after* reading potential error stream
        if (conn.getResponseCode() != 201) {
            String errorResponse = readError(conn); // Read error stream if not 201
            throw new IOException("Failed to create prescription: HTTP error code " + conn.getResponseCode() + " - " + errorResponse);
        }
        conn.disconnect(); // Disconnect after processing response

        return "Success"; // Deserialize the created object
    }


    // --- Helper Methods (aligned for better consistency and error reading) ---

    private HttpURLConnection createConnection(String urlStr, String method) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(method);
        conn.setRequestProperty("Accept", "application/json");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        if ("POST".equals(method) || "PUT".equals(method)) {
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
        }
        return conn;
    }

    private void writeRequestBody(HttpURLConnection conn, String jsonInputString) throws IOException {
        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
            os.flush();
        }
    }

    // Helper to read successful responses
    private String readResponse(HttpURLConnection conn) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            return br.lines().collect(Collectors.joining());
        }
    }

    // Helper to read error responses
    private String readError(HttpURLConnection conn) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream() != null ? conn.getErrorStream() : conn.getInputStream(), StandardCharsets.UTF_8))) {
            return br.lines().collect(Collectors.joining());
        } catch (IOException e) {
            System.err.println("Error reading error stream: " + e.getMessage());
            return "Failed to read error response.";
        }
    }

    private void checkResponse(HttpURLConnection conn) throws IOException {
        int status = conn.getResponseCode();
        if (status != 200 && status != 201) { // Allow 201 for creation
            String errorResponse = readError(conn);
            throw new IOException("Request failed with status " + status + ": " + errorResponse);
        }
    }

    public static void main(String[] args) {
        PrescriptionFetcher fetcher = new PrescriptionFetcher();

        try {
            System.out.println("--- Testing Prescription Fetcher Functionality ---");

            // Test 1: createPrescription()
            System.out.println("\n1. Creating a new prescription...");
            Long testPatientId = 1L; // Example patient ID
            Long testDoctorId = 3L;  // Example doctor ID
            List<Medicine> testMedicines = Arrays.asList(
                    new Medicine("Paracetamol", "Pain relief", "500mg", new BigDecimal(32000)),
                    new Medicine("Amoxicillin", "Antibiotic", "250mg", new BigDecimal(48000))
            );
            PrescriptionRequest newPrescriptionRequest = new PrescriptionRequest(
                    testPatientId,
                    testDoctorId,
                    testMedicines
                    // No date needed, assuming it's set on backend
            );
            System.out.println("  Request: " + newPrescriptionRequest);

            String createdPrescription = fetcher.createPrescription(newPrescriptionRequest); // Method now returns PrescriptionResponse
            System.out.println("  New Prescription Created: " + createdPrescription);

            // Test 2: getAllPrescriptions()
            System.out.println("\n2. Fetching all prescriptions...");
            List<PrescriptionResponse> allPrescriptions = fetcher.getAllPrescriptions();
            if (allPrescriptions.isEmpty()) {
                System.out.println("  No prescriptions found.");
            } else {
                System.out.println("  All Prescriptions:");
                for (PrescriptionResponse prescription : allPrescriptions) {
                    System.out.println("    " + prescription);
                }
            }

            // Test 3: getPrescriptionsByUserId()
            System.out.println("\n3. Fetching prescriptions for patient ID: " + testPatientId + "...");
            List<PrescriptionResponse> prescriptionsByPatient = fetcher.getPrescriptionsByUserId(testPatientId);
            if (prescriptionsByPatient.isEmpty()) {
                System.out.println("  No prescriptions found for patient ID: " + testPatientId + ".");
            } else {
                System.out.println("  Prescriptions for patient " + testPatientId + ":");
                for (PrescriptionResponse prescription : prescriptionsByPatient) {
                    System.out.println("    " + prescription);
                }
            }

            // Test 4: getPrescriptionsByUserId() with a non-existent user
            Long nonExistentUserId = 99999L;
            System.out.println("\n4. Fetching prescriptions for non-existent user ID: " + nonExistentUserId + "...");
            try {
                List<PrescriptionResponse> noPrescriptions = fetcher.getPrescriptionsByUserId(nonExistentUserId);
                if (noPrescriptions.isEmpty()) {
                    System.out.println("  (Expected) No prescriptions found for non-existent user ID: " + nonExistentUserId + ".");
                } else {
                    System.out.println("  Unexpectedly found prescriptions for non-existent user: " + noPrescriptions);
                }
            } catch (IOException e) {
                System.out.println("  (Expected) Request failed for non-existent user: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("\nAn IOException occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("\nAn unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("\n--- Prescription Fetcher Test Finished ---");
        }
    }
}