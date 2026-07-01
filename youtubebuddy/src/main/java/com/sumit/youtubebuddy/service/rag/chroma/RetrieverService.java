package com.sumit.youtubebuddy.service.rag.chroma;

import com.sumit.youtubebuddy.service.rag.EmbeddingService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RetrieverService {

    private final EmbeddingService embeddingService;
    private final ChromaService chromaService;

    public RetrieverService(
            EmbeddingService embeddingService,
            ChromaService chromaService
    ) {
        this.embeddingService = embeddingService;
        this.chromaService = chromaService;
    }

    public String retrieve(
            String question
    ) {

        Map embeddingResponse =
                embeddingService.generateEmbedding(
                        question
                );

        Object embedding =
                embeddingResponse.get(
                        "embedding"
                );

        return chromaService.search(
                embedding,
                question
        );
    }
}