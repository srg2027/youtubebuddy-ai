package com.sumit.youtubebuddy.controller;

import com.sumit.youtubebuddy.dto.AskRequest;
import com.sumit.youtubebuddy.dto.AskResponse;
import com.sumit.youtubebuddy.dto.VideoRequest;
import com.sumit.youtubebuddy.dto.rag.ChatResponse;
import com.sumit.youtubebuddy.dto.rag.RetrievalResult;
import com.sumit.youtubebuddy.service.RagChatService;
import com.sumit.youtubebuddy.service.rag.CitationMapper;
import com.sumit.youtubebuddy.service.rag.EmbeddingService;
import com.sumit.youtubebuddy.service.rag.IngestionService;
import com.sumit.youtubebuddy.service.rag.chroma.ChromaService;
import com.sumit.youtubebuddy.service.rag.chroma.RetrieverService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;

@RestController
public class RagController {

    private final EmbeddingService embeddingService;
    private final RagChatService ragChatService;
    private final ChromaService chromaService;
    private final RetrieverService retrieverService;
    private final CitationMapper citationMapper;
    private final IngestionService ingestionService;

    public RagController(
            EmbeddingService embeddingService,
            ChromaService chromaService,
            RagChatService ragChatService,
            RetrieverService retrieverService,
            CitationMapper citationMapper,
            IngestionService ingestionService
    ) {
        this.embeddingService = embeddingService;
        this.chromaService = chromaService;
        this.ragChatService = ragChatService;
        this.retrieverService = retrieverService;
        this.citationMapper = citationMapper;
        this.ingestionService = ingestionService;
    }

    @GetMapping("/test-embedding")
    public Object testEmbedding() {

        return embeddingService.generateEmbedding(
                "Spring Boot RAG"
        );
    }

    @GetMapping("/create-collection")
    public String createCollection() {

        return chromaService.createCollection();
    }

    @GetMapping("/test-localhost")
    public String testLocalhost() {

        try {
            return InetAddress
                    .getByName("localhost")
                    .getHostAddress();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @PostMapping("/store-transcript")
    public String storeTranscript(
            @RequestBody VideoRequest request
    ) {

        return ingestionService.ingestVideo(
                request.getYoutubeUrl()
        );
    }


    @GetMapping("/retrieve-test")
    public String retrieveTest() {

        RetrievalResult result =
                retrieverService.retrieve(
                        "What is this video about?"
                );

        return result.getContext();
    }
    @GetMapping("/debug-search")
    public RetrievalResult debugSearch(
            @RequestParam String question
    ) {
        return retrieverService.retrieve(question);
    }

    @PostMapping("/ask")
    public ChatResponse askQuestion(
            @RequestBody AskRequest request
    ) {

        return ragChatService.askQuestion(
                        request.getQuestion(),
                        request.getModel()
                );
    }
}