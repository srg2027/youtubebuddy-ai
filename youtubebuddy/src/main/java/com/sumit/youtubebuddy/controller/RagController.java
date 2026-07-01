package com.sumit.youtubebuddy.controller;

import com.sumit.youtubebuddy.service.RagChatService;
import com.sumit.youtubebuddy.service.rag.EmbeddingService;
import com.sumit.youtubebuddy.service.rag.chroma.ChromaService;
import com.sumit.youtubebuddy.service.rag.chroma.RetrieverService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sumit.youtubebuddy.dto.QuestionRequest;
import com.sumit.youtubebuddy.dto.AnswerResponse;
import com.sumit.youtubebuddy.dto.AskRequest;
import com.sumit.youtubebuddy.dto.AskResponse;

import com.sumit.youtubebuddy.dto.VideoRequest;
import com.sumit.youtubebuddy.service.TranscriptService;
import com.sumit.youtubebuddy.service.rag.TextChunkingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

@RestController
public class RagController {

    private final EmbeddingService embeddingService;
    private final RagChatService ragChatService;
    private final ChromaService chromaService;
    private final TranscriptService transcriptService;
    private final TextChunkingService textChunkingService;
    private final RetrieverService retrieverService;

    public RagController(
            EmbeddingService embeddingService,
            ChromaService chromaService,
            RagChatService ragChatService,
            RetrieverService retrieverService,
            TranscriptService transcriptService,
            TextChunkingService textChunkingService) {

        this.embeddingService = embeddingService;
        this.ragChatService = ragChatService;
        this.retrieverService = retrieverService;
        this.chromaService = chromaService;
        this.transcriptService = transcriptService;
        this.textChunkingService = textChunkingService;
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
    @GetMapping("/store-test")
    public String storeTest() {

        String text =
                "Spring Boot and ChromaDB integration test";

        Object embedding =
                embeddingService.generateEmbedding(text)
                        .get("embedding");

        return chromaService.addEmbedding(
                "doc-1",
                text,
                embedding
        );
    }
    @PostMapping("/store-transcript")
    public String storeTranscript(
            @RequestBody VideoRequest request) {

        String transcript =
                transcriptService.getTranscript(
                        request.getYoutubeUrl()
                );

        List<String> chunks =
                textChunkingService.chunkText(
                        transcript
                );

        int count = 0;

        for (String chunk : chunks) {

            Map<String, Object> embeddingResponse =
                    embeddingService.generateEmbedding(
                            chunk
                    );

            Object embedding =
                    embeddingResponse.get(
                            "embedding"
                    );

            chromaService.addEmbedding(
                    "chunk-" + count,
                    chunk,
                    embedding
            );

            count++;
        }

        return "Stored " + count + " chunks";
    }
    @GetMapping("/retrieve-test")
    public String retrieveTest() {

        return retrieverService.retrieve(
                "What is this video about?"
        );
    }

    @PostMapping("/ask")
    public AskResponse askQuestion(
            @RequestBody AskRequest request
    ) {

        String answer =
                ragChatService.askQuestion(
                        request.getQuestion()
                );

        return new AskResponse(answer);
    }
}