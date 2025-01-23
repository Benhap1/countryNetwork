package ru.skillbox.country.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtParser {

    private final ObjectMapper objectMapper;

    public String getEmailFromToken(String token) {
        try {
            String[] tokenParts = token.split("\\.");
            if (tokenParts.length != 3) {
                throw new IllegalArgumentException("Неправильный формат токена");
            }
            String payload = new String(Base64.getDecoder().decode(tokenParts[1]));

            Map<String, Object> claims = objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {
            });

            return claims.get("email").toString();
        } catch (JsonProcessingException e) {
            log.error("Ошибка чтения токена {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
