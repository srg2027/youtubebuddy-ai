package com.sumit.youtubebuddy.service.rag;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class EmbeddingService {

    private final RestTemplate restTemplate =
            new RestTemplate();

    public Map<String, Object> generateEmbedding(String text) {

        String url =
                "http://localhost:11434/api/embeddings";

        Map<String, Object> request =
                Map.of(
                        "model",
                        "nomic-embed-text",
                        "prompt",
                        text
                );

        ResponseEntity<Map> response =
                restTemplate.postForEntity(
                        url,
                        request,
                        Map.class
                );

        return response.getBody();
    }
}