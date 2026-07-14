package com.sumit.youtubebuddy.service.model.ollama;

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
public class OllamaChatModel implements ChatModel {

    @Value("${ollama.base.url}")
    private String baseUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public OllamaChatModel(
            RestTemplate restTemplate,
            ObjectMapper objectMapper
    ) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(AIModel model) {
        return model.getProvider() == AIProvider.OLLAMA;
    }

    @Override
    public String generateResponse(
            String prompt,
            AIModel model
    ) {

        try {

            HttpHeaders headers =
                    new HttpHeaders();

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
                            ),
                            "stream", false
                    );

            HttpEntity<Map<String, Object>> request =
                    new HttpEntity<>(
                            body,
                            headers
                    );

            ResponseEntity<String> response =
                    restTemplate.postForEntity(
                            baseUrl + "/api/chat",
                            request,
                            String.class
                    );

            JsonNode json =
                    objectMapper.readTree(
                            response.getBody()
                    );

            return json.get("message")
                    .get("content")
                    .asText();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to call Ollama",
                    e
            );
        }
    }
}