package com.sumit.youtubebuddy.service.rag.chroma;

import com.sumit.youtubebuddy.dto.rag.RetrievalResult;
import com.sumit.youtubebuddy.service.rag.EmbeddingService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GenericRetrieverService {

    private final EmbeddingService embeddingService;
    private final ChromaService chromaService;

    public GenericRetrieverService(
            EmbeddingService embeddingService,
            ChromaService chromaService
    ) {
        this.embeddingService = embeddingService;
        this.chromaService = chromaService;
    }

    public RetrievalResult retrieve(CollectionType type, String question) {
        Map embeddingResponse = embeddingService.generateEmbedding(question);
        Object embedding = embeddingResponse.get("embedding");

        if (embedding instanceof java.util.List<?> list) {
            System.out.println("Query Embedding Dimension = " + list.size());
        }

        return chromaService.search(type, embedding, question);
    }
}
