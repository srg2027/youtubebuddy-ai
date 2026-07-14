package com.sumit.youtubebuddy.service.rag;

import com.sumit.youtubebuddy.dto.rag.SemanticChunk;
import com.sumit.youtubebuddy.dto.transcript.TranscriptResponse;
import com.sumit.youtubebuddy.dto.rag.DocumentMetadata;
import com.sumit.youtubebuddy.service.TranscriptService;
import com.sumit.youtubebuddy.service.rag.chroma.ChromaService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class IngestionService {

    private final TranscriptService transcriptService;
    private final SemanticChunkingService semanticChunkingService;
    private final EmbeddingService embeddingService;
    private final ChromaService chromaService;

    public IngestionService(
            TranscriptService transcriptService,
            SemanticChunkingService semanticChunkingService,

            EmbeddingService embeddingService,
            ChromaService chromaService
    ) {
        this.transcriptService = transcriptService;
        this.semanticChunkingService = semanticChunkingService;
        this.embeddingService = embeddingService;
        this.chromaService = chromaService;
    }

    public String ingestVideo(String youtubeUrl) {

        TranscriptResponse transcriptResponse =
                transcriptService.getTranscript(
                        youtubeUrl
                );

        String transcript =
                transcriptResponse.getTranscript();

        List<SemanticChunk> chunks =
                semanticChunkingService.chunk(
                        transcriptResponse.getSegments()
                );

        int count = 0;

        for (SemanticChunk chunk : chunks) {

            DocumentMetadata metadata =
                    new DocumentMetadata();
            metadata.setSourceType(com.sumit.youtubebuddy.dto.rag.SourceType.YOUTUBE);
            metadata.setVideoId(transcriptResponse.getVideoId());
            metadata.setVideoTitle(transcriptResponse.getVideoTitle());
            metadata.setVideoUrl(transcriptResponse.getVideoUrl());
            metadata.setChunkIndex(count);
            metadata.setStartMillis(chunk.getStartMillis());
            metadata.setEndMillis(chunk.getEndMillis());

            Map<String, Object> embeddingResponse =
                    embeddingService.generateEmbedding(
                            chunk.getText()
                    );

            Object embedding =
                    embeddingResponse.get(
                            "embedding"
                    );

            String chunkId =
                    transcriptResponse.getVideoId()
                            + "-chunk-"
                            + count;

            chromaService.addEmbedding(
                    com.sumit.youtubebuddy.service.rag.chroma.CollectionType.YOUTUBE,
                    chunkId,
                    chunk.getText(),
                    embedding,
                    metadata
            );

            count++;
        }

        return "Stored " + count + " chunks successfully.";
    }
}