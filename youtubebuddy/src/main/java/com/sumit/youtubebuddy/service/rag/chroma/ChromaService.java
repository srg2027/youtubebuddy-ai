package com.sumit.youtubebuddy.service.rag.chroma;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ChromaService {

    private final RestTemplate restTemplate;

    public ChromaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String createCollection() {

        String url =
                "http://localhost:8000/api/v2/tenants/default_tenant/databases/default_database/collections";

        Map<String, Object> body =
                Map.of(
                        "name",
                        "youtube_transcripts_v2"
                );

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        url,
                        body,
                        String.class
                );

        return response.getBody();
    }
    public String addEmbedding(
            String id,
            String document,
            Object embedding
    ) {

        String url =
                "http://localhost:8000/api/v2/tenants/default_tenant/databases/default_database/collections/86338d84-c248-4d5d-a2cc-f61d02e026e5/add";

        Map<String, Object> body =
                Map.of(
                        "ids", new String[]{id},
                        "documents", new String[]{document},
                        "embeddings", new Object[]{embedding}
                );

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        url,
                        body,
                        String.class
                );

        return response.getBody();
    }
    public String search(
            Object embedding
    ) {

        String url =
                "http://localhost:8000/api/v2/tenants/default_tenant/databases/default_database/collections/86338d84-c248-4d5d-a2cc-f61d02e026e5/query";

        Map<String, Object> body =
                Map.of(
                        "query_embeddings",
                        new Object[]{embedding},

                        "n_results",
                        5
                );

        ResponseEntity<String> response =
                restTemplate.postForEntity(
                        url,
                        body,
                        String.class
                );

        return response.getBody();
    }
}