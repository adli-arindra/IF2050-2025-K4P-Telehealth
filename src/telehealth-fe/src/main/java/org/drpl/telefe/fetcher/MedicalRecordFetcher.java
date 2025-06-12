package org.drpl.telefe.fetcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.drpl.telefe.Global;
import org.drpl.telefe.domain.MedicalRecord;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;

public class MedicalRecordFetcher {
    private static final String BASE_URL = "http://localhost:8080/medical-records";
    private final ObjectMapper objectMapper;

    public MedicalRecordFetcher() {
        objectMapper = Global.getObjectMapper();
    }

    public MedicalRecord getByUserId(Long userId) throws IOException {
        URL url = new URL(BASE_URL + "/" + userId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int status = connection.getResponseCode();
        if (status == 200) {
            try (InputStream inputStream = connection.getInputStream()) {
                return objectMapper.readValue(inputStream, MedicalRecord.class);
            }
        } else if (status == 404) {
            return null;
        } else {
            throw new IOException("Failed to fetch medical record: " + status);
        }
    }

    public void save(Long userId, MedicalRecord record) throws IOException {
        URL url = new URL(BASE_URL + "/" + userId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        String json = objectMapper.writeValueAsString(record);

        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = json.getBytes(StandardCharsets.UTF_8);
            os.write(input);
        }

        int status = connection.getResponseCode();
        if (status != 200 && status != 201) {
            throw new IOException("Failed to save medical record: " + status);
        }
    }

    public static void main(String[] args) {
        MedicalRecordFetcher fetcher = new MedicalRecordFetcher();
        Long testUserId = 1L;

        try {
            MedicalRecord existing = fetcher.getByUserId(testUserId);
            if (existing != null) {
                System.out.println("Existing Medical Record:");
                System.out.println("Diagnosis: " + existing.getDiagnosis());
                System.out.println("Treatment: " + existing.getTreatment());
                System.out.println("Record Date: " + existing.getRecordDate());
            } else {
                System.out.println("No existing medical record found. Creating one...");

                MedicalRecord newRecord = new MedicalRecord();
                newRecord.setDiagnosis("Mild flu");
                newRecord.setTreatment("Rest, fluids, and paracetamol");
                newRecord.setRecordDate(new Date());

                fetcher.save(testUserId, newRecord);
                System.out.println("Medical record saved successfully.");
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
