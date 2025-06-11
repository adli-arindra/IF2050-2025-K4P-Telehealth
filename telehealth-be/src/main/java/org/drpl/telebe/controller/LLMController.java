package org.drpl.telebe.controller;

import org.drpl.telebe.dto.DoctorSpecializationType;
import org.drpl.telebe.dto.LLMQueryRequest;
import org.drpl.telebe.model.MedicalRecord;
import org.drpl.telebe.repository.MedicalRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.json.JSONObject;


import java.util.*;

@RestController
@RequestMapping("/llm")
public class LLMController {
    @Autowired
    private MedicalRecordRepository medicalRecordRepository;

    @Value("${gemini.api.key}")
    private String apiKey;

    private static final String GEMINI_ENDPOINT =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";


    private final String SYSTEM_TEXT = generateEnumSystemText(DoctorSpecializationType.class);


    @PostMapping("")
    public ResponseEntity<String> queryLLM(@RequestBody LLMQueryRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String prompt = generateConditionPrompt(request.getPatientId());

        // system_instruction structure
        Map<String, Object> systemInstruction = Map.of(
                "parts", List.of(Map.of("text", SYSTEM_TEXT + prompt))
        );

        // user content structure
        Map<String, Object> userContent = Map.of(
                "parts", List.of(Map.of("text", request.getQuery()))
        );

        // complete request body
        Map<String, Object> body = Map.of(
                "system_instruction", systemInstruction,
                "contents", List.of(userContent)
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        String url = GEMINI_ENDPOINT + apiKey;

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            String enumResponse = extractAnswer(response.getBody());
            return ResponseEntity.ok(enumResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }


    public String extractAnswer(String json) {
        JSONObject obj = new JSONObject(json);
        String rawText = obj
                .getJSONArray("candidates")
                .getJSONObject(0)
                .getJSONObject("content")
                .getJSONArray("parts")
                .getJSONObject(0)
                .getString("text");

        return rawText.trim().replaceAll("\\s+", "");
    }


    private <E extends Enum<E>> String generateEnumSystemText(Class<E> enumClass) {
        StringBuilder builder = new StringBuilder();
        builder.append("You must only reply with one of the following enum values: ");

        E[] constants = enumClass.getEnumConstants();
        for (int i = 0; i < constants.length; i++) {
            builder.append(constants[i].name());
            if (i < constants.length - 1) {
                builder.append(", ");
            }
        }

        builder.append(". Do not include any explanation or extra text. Reply only with the value. ");
        return builder.toString();
    }

    private String generateConditionPrompt(long patientId) {
        MedicalRecord record = medicalRecordRepository.findByPatientId(patientId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials."));

        return "I have a condition of " + record.getDiagnosis() +
                ". And I am currently getting the treatment of " + record.getTreatment() +
                ". Please tell me what doctor specialization I need to consult.";
    }
}
