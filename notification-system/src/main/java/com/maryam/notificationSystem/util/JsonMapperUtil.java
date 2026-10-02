package com.maryam.notificationSystem.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author M.Ezati
 * 06/05/2026
 */
@Component
public class JsonMapperUtil {
    private static final Logger log = LoggerFactory.getLogger(JsonMapperUtil.class);
    private final ObjectMapper objectMapper;

    public JsonMapperUtil(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T deserialize(String raw, Class<T> clazz) {
        try {
            String jsonRaw = unwrapIfNeeded(raw);
            return objectMapper.readValue(jsonRaw, clazz);
        } catch (JsonProcessingException e) {
            log.error("Failed to deserialize to {} : {}", clazz.getSimpleName(), e.getOriginalMessage());
            return null;
        }
    }

    public String serialize(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize object {} : {}",object.getClass().getSimpleName(),e.getOriginalMessage());
            return null;
        }
    }

    private String unwrapIfNeeded(String raw) {
        if (raw == null) {
            return null;
        }
        raw = raw.trim();
        if (raw.startsWith("\"") && raw.endsWith("\"")) {
            raw = raw.substring(1, raw.length() - 1);
            raw = raw.replace("\\\"", "\"");
        }
        return raw;
    }
}

