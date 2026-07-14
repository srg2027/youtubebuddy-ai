package com.sumit.youtubebuddy.service.rag;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class EmbeddingService {

    private final RestTemplate restTemplate;

    public EmbeddingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

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

        Map<String, Object> result = response.getBody();

        System.out.println("\n========== EMBEDDING ==========");

        Object embedding = result.get("embedding");

        if (embedding instanceof java.util.List<?> list) {
            System.out.println("Embedding Dimension = " + list.size());
            
            // Normalize embedding vector to unit length
            java.util.List<Double> doubleList = new java.util.ArrayList<>();
            double sumSq = 0.0;
            for (Object obj : list) {
                double val = ((Number) obj).doubleValue();
                doubleList.add(val);
                sumSq += val * val;
            }
            double norm = Math.sqrt(sumSq);
            if (norm > 0.0) {
                for (int i = 0; i < doubleList.size(); i++) {
                    doubleList.set(i, doubleList.get(i) / norm);
                }
            }
            
            java.util.Map<String, Object> modifiableResult = new java.util.HashMap<>(result);
            modifiableResult.put("embedding", doubleList);
            result = modifiableResult;
        }

        System.out.println("===============================\n");

        return result;
    }
}