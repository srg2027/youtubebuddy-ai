package com.sumit.youtubebuddy.service.model.groq;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumit.youtubebuddy.service.model.AIModel;
import com.sumit.youtubebuddy.service.model.AIProvider;
import com.sumit.youtubebuddy.service.model.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class GroqChatModel implements ChatModel {

    @Value("${groq.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public GroqChatModel(
            RestTemplate restTemplate,
            ObjectMapper objectMapper
    ) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(AIModel model) {
        return model.getProvider() == AIProvider.GROQ;
    }

    @Override
    public String generateResponse(
            String prompt,
            AIModel model
    ) {

        try {

            HttpHeaders headers = new HttpHeaders();

            headers.setBearerAuth(apiKey);

            headers.setContentType(
                    MediaType.APPLICATION_JSON
            );

            Map<String, Object> body =
                    Map.of(
                            "model", model.getModelName(),
                            "messages",
                            List.of(
                                    Map.of(
                                            "role", "user",
                                            "content", prompt
                                    )
                            )
                    );

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(
                            body,
                            headers
                    );

            ResponseEntity<String> response =
                    restTemplate.postForEntity(
                            "https://api.groq.com/openai/v1/chat/completions",
                            request,
                            String.class
                    );

            JsonNode json =
                    objectMapper.readTree(
                            response.getBody()
                    );

            return json.get("choices")
                    .get(0)
                    .get("message")
                    .get("content")
                    .asText();

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to call Groq",
                    e
            );
        }
    }
}