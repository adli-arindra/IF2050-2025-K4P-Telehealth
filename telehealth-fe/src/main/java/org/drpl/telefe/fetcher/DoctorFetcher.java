package org.drpl.telefe.fetcher;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.drpl.telefe.model.Doctor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class DoctorFetcher {

    private static final String BASE_URL = "http://localhost:8080/doctors";
    private final ObjectMapper objectMapper = new ObjectMapper();

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
}
