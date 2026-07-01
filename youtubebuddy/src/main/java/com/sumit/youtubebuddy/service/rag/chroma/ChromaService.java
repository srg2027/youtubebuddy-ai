package com.sumit.youtubebuddy.service.rag.chroma;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumit.youtubebuddy.dto.chroma.ChromaQueryResponse;
import com.sumit.youtubebuddy.dto.chroma.ChromaResult;
import com.sumit.youtubebuddy.service.rag.AdaptiveRetrieverService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChromaService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ContextBuilderService contextBuilderService;
    private final AdaptiveRetrieverService adaptiveRetrieverService;

    public ChromaService(
            RestTemplate restTemplate,
            ObjectMapper objectMapper,
            ContextBuilderService contextBuilderService,
            AdaptiveRetrieverService adaptiveRetrieverService
    ) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.contextBuilderService = contextBuilderService;
        this.adaptiveRetrieverService = adaptiveRetrieverService;
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

    public String search(Object embedding,String question) {
        int topK =
                adaptiveRetrieverService
                        .determineTopK(question);

        System.out.println("Top-K = " + topK);

        try {

            String url =
                    "http://localhost:8000/api/v2/tenants/default_tenant/databases/default_database/collections/86338d84-c248-4d5d-a2cc-f61d02e026e5/query";

            Map<String, Object> body =
                    Map.of(
                            "query_embeddings",
                            new Object[]{embedding},
                            "n_results",
                                topK
                    );

            ResponseEntity<String> response =
                    restTemplate.postForEntity(
                            url,
                            body,
                            String.class
                    );

            ChromaQueryResponse chromaResponse =
                    objectMapper.readValue(
                            response.getBody(),
                            ChromaQueryResponse.class
                    );

            System.out.println("\n========== CHROMA RESPONSE ==========");

            System.out.println("Documents:");
            System.out.println(chromaResponse.getDocuments());

            System.out.println("\nDistances:");
            System.out.println(chromaResponse.getDistances());

            System.out.println("\nIDs:");
            System.out.println(chromaResponse.getIds());

            System.out.println("=====================================\n");

            List<ChromaResult> results =
                    mapResults(chromaResponse);

            String context =
                    contextBuilderService.buildContext(results);

            System.out.println("\n========== CLEAN CONTEXT ==========");
            System.out.println(context);
            System.out.println("===================================\n");

            return context;

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to parse Chroma response",
                    e
            );
        }
    }

    private List<ChromaResult> mapResults(
            ChromaQueryResponse response
    ) {

        List<ChromaResult> results = new ArrayList<>();

        List<String> documents =
                response.getDocuments().get(0);

        List<Double> distances =
                response.getDistances().get(0);

        List<String> ids =
                response.getIds().get(0);

        for (int i = 0; i < documents.size(); i++) {

            results.add(
                    new ChromaResult(
                            documents.get(i),
                            distances.get(i),
                            ids.get(i)
                    )
            );
        }

        return results;
    }
}