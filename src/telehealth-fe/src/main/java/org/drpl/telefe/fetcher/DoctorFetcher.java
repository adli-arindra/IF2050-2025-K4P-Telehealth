package org.drpl.telefe.fetcher;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.drpl.telefe.Global;
import org.drpl.telefe.domain.Doctor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DoctorFetcher {

    private static final String BASE_URL = "http://localhost:8080/doctors";
    private final ObjectMapper objectMapper = Global.getObjectMapper();

    public List<Doctor> getAllDoctors() throws IOException {
        HttpURLConnection conn = createConnection(BASE_URL);
        checkResponse(conn);
        return objectMapper.readValue(conn.getInputStream(), new TypeReference<List<Doctor>>() {
        });
    }

    public List<Doctor> getDoctorsBySpecialization(String specialization) throws IOException {
        HttpURLConnection conn = createConnection(BASE_URL + "/by-specialization?specialization=" + specialization);
        checkResponse(conn);
        return objectMapper.readValue(conn.getInputStream(), new TypeReference<List<Doctor>>() {
        });
    }

    public List<String> getAllSpecializations() throws IOException {
        HttpURLConnection conn = createConnection(BASE_URL + "/specializations");
        checkResponse(conn);
        return objectMapper.readValue(conn.getInputStream(), new TypeReference<List<String>>() {
        });
    }

    private HttpURLConnection createConnection(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        return conn;
    }

    private void checkResponse(HttpURLConnection conn) throws IOException {
        int status = conn.getResponseCode();
        if (status != 200) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()))) {
                String error = br.readLine();
                throw new IOException("Request failed with status " + status + ": " + error);
            }
        }
    }

    public static void main(String[] args) {
        DoctorFetcher fetcher = new DoctorFetcher();

        try {
            System.out.println("--- Testing Doctor Fetcher Functionality ---");

            // Test 1: getAllDoctors()
            System.out.println("\n1. Fetching all doctors...");
            List<Doctor> allDoctors = fetcher.getAllDoctors();
            if (allDoctors.isEmpty()) {
                System.out.println("  No doctors found.");
            } else {
                System.out.println("  All Doctors:");
                for (Doctor doctor : allDoctors) {
                    System.out.println("    " + doctor);
                }
            }

            // Test 2: getAllSpecializations()
            System.out.println("\n2. Fetching all specializations...");
            List<String> specializations = fetcher.getAllSpecializations();
            if (specializations.isEmpty()) {
                System.out.println("  No specializations found.");
            } else {
                System.out.println("  All Specializations: " + specializations);
            }

            // Test 3: getDoctorsBySpecialization()
            // Use a specialization that you expect to exist in your backend
            String testSpecialization = "GENERAL_PRACTICE"; // Example specialization
            System.out.println("\n3. Fetching doctors by specialization: '" + testSpecialization + "'...");
            List<Doctor> doctorsBySpecialization = fetcher.getDoctorsBySpecialization(testSpecialization);
            if (doctorsBySpecialization.isEmpty()) {
                System.out.println("  No doctors found for specialization: '" + testSpecialization + "'.");
            } else {
                System.out.println("  Doctors in '" + testSpecialization + "':");
                for (Doctor doctor : doctorsBySpecialization) {
                    System.out.println("    " + doctor);
                }
            }

            // Test 4: getDoctorsBySpecialization() with a non-existent specialization
            String nonExistentSpecialization = "NON_EXISTENT_SPECIALIZATION";
            System.out.println("\n4. Fetching doctors by non-existent specialization: '" + nonExistentSpecialization + "'...");
            try {
                List<Doctor> noDoctors = fetcher.getDoctorsBySpecialization(nonExistentSpecialization);
                if (noDoctors.isEmpty()) {
                    System.out.println("  (Expected) No doctors found for '" + nonExistentSpecialization + "'.");
                } else {
                    System.out.println("  Unexpectedly found doctors for '" + nonExistentSpecialization + "': " + noDoctors);
                }
            } catch (IOException e) {
                System.out.println("  (Expected) Request failed for non-existent specialization: " + e.getMessage());
            }

        } catch (IOException e) {
            System.err.println("\nAn IOException occurred: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("\nAn unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("\n--- Doctor Fetcher Test Finished ---");
        }
    }
}
