package com.sumit.youtubebuddy.service.rag.chroma;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumit.youtubebuddy.dto.chroma.ChromaQueryResponse;
import com.sumit.youtubebuddy.dto.chroma.ChromaResult;
import com.sumit.youtubebuddy.service.rag.AdaptiveRetrieverService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.sumit.youtubebuddy.dto.rag.RetrievalResult;
import com.sumit.youtubebuddy.dto.rag.DocumentMetadata;
import java.util.Collections;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ChromaService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ContextBuilderService contextBuilderService;
    private final AdaptiveRetrieverService adaptiveRetrieverService;
    private final ChromaMetadataMapper chromaMetadataMapper;
    private final ChromaResultMapper resultMapper;
    private final CollectionManagerService collectionManagerService;

    public ChromaService(
            RestTemplate restTemplate,
            ObjectMapper objectMapper,
            ContextBuilderService contextBuilderService,
            AdaptiveRetrieverService adaptiveRetrieverService,
            ChromaMetadataMapper chromaMetadataMapper,
            ChromaResultMapper resultMapper,
            CollectionManagerService collectionManagerService
    ) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;

        this.contextBuilderService = contextBuilderService;
        this.adaptiveRetrieverService = adaptiveRetrieverService;
        this.chromaMetadataMapper = chromaMetadataMapper;
        this.resultMapper = resultMapper;
        this.collectionManagerService = collectionManagerService;
    }

    public String createCollection() {
        return collectionManagerService.getOrCreateCollectionId(CollectionType.YOUTUBE);
    }

    public String addEmbedding(
            CollectionType collectionType,
            String id,
            String document,
            Object embedding,
            Map<String, Object> metadata
    ) {
        String collectionId = collectionManagerService.getOrCreateCollectionId(collectionType);
        String url =
                "http://localhost:8000/api/v2/tenants/default_tenant/databases/default_database/collections/" + collectionId + "/add";

        Map<String, Object> body =
                Map.of(
                        "ids", new String[]{id},
                        "documents", new String[]{document},
                        "embeddings", new Object[]{embedding},
                        "metadatas", new Object[]{metadata}
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
            CollectionType collectionType,
            String id,
            String document,
            Object embedding,
            DocumentMetadata metadataObj
    ) {
        Map<String, Object> metadata = java.util.Map.ofEntries(
                java.util.Map.entry("sourceType", metadataObj.getSourceType() != null ? metadataObj.getSourceType().name() : ""),
                java.util.Map.entry("sourceName", metadataObj.getSourceName() != null ? metadataObj.getSourceName() : ""),
                java.util.Map.entry("videoId", metadataObj.getVideoId() != null ? metadataObj.getVideoId() : ""),
                java.util.Map.entry("videoTitle", metadataObj.getVideoTitle() != null ? metadataObj.getVideoTitle() : ""),
                java.util.Map.entry("videoUrl", metadataObj.getVideoUrl() != null ? metadataObj.getVideoUrl() : ""),
                java.util.Map.entry("fileName", metadataObj.getFileName() != null ? metadataObj.getFileName() : ""),
                java.util.Map.entry("pageTitle", metadataObj.getPageTitle() != null ? metadataObj.getPageTitle() : ""),
                java.util.Map.entry("url", metadataObj.getUrl() != null ? metadataObj.getUrl() : ""),
                java.util.Map.entry("chunkIndex", metadataObj.getChunkIndex()),
                java.util.Map.entry("pageNumber", metadataObj.getPageNumber()),
                java.util.Map.entry("startMillis", metadataObj.getStartMillis()),
                java.util.Map.entry("endMillis", metadataObj.getEndMillis())
        );

        return addEmbedding(collectionType, id, document, embedding, metadata);
    }

    public RetrievalResult search(Object embedding, String question) {
        return search(CollectionType.YOUTUBE, embedding, question);
    }

    public RetrievalResult search(CollectionType collectionType, Object embedding, String question) {
        int topK =
                adaptiveRetrieverService
                        .determineTopK(question);

        System.out.println("Top-K = " + topK);

        try {
            String collectionId = collectionManagerService.getOrCreateCollectionId(collectionType);
            String url =
                    "http://localhost:8000/api/v2/tenants/default_tenant/databases/default_database/collections/" + collectionId + "/query";

            Map<String, Object> body =
                    Map.of(
                            "query_embeddings",
                            new Object[]{embedding},
                            "n_results",
                            topK,
                            "include",
                            List.of("documents", "distances", "metadatas")
                    );

            ResponseEntity<String> response =
                    restTemplate.postForEntity(
                            url,
                            body,
                            String.class
                    );
            System.out.println("\n================ RAW CHROMA RESPONSE ================");
            System.out.println(response.getBody());
            System.out.println("=====================================================\n");

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
                    resultMapper.map(
                            chromaResponse
                    );

            String context =
                    contextBuilderService.buildContext(results);

            System.out.println("\n========== CLEAN CONTEXT ==========");
            System.out.println(context);
            System.out.println("===================================\n");

            return new RetrievalResult(
                    context,
                    results
            );

        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to parse Chroma response",
                    e
            );
        }
    }


}