package org.drpl.telefe.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String input = p.getText();
        if (input.contains(".")) {
            int dot = input.indexOf('.');
            int end = Math.min(dot + 4, input.length()); // dot + 3 ms digits
            input = input.substring(0, end);
        }
        return LocalDateTime.parse(input, FORMATTER);
    }
}
