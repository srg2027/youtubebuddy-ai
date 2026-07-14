package com.sumit.youtubebuddy.service.rag.pdf;

import com.sumit.youtubebuddy.dto.pdf.PdfMetadata;
import com.sumit.youtubebuddy.service.rag.DocumentChunkingService;
import com.sumit.youtubebuddy.service.rag.EmbeddingService;
import com.sumit.youtubebuddy.service.rag.chroma.ChromaService;
import com.sumit.youtubebuddy.service.rag.chroma.CollectionType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PdfIngestionService {

    private final PdfExtractionService pdfExtractionService;
    private final DocumentChunkingService documentChunkingService;
    private final EmbeddingService embeddingService;
    private final ChromaService chromaService;

    public PdfIngestionService(
            PdfExtractionService pdfExtractionService,
            DocumentChunkingService documentChunkingService,
            EmbeddingService embeddingService,
            ChromaService chromaService
    ) {
        this.pdfExtractionService = pdfExtractionService;
        this.documentChunkingService = documentChunkingService;
        this.embeddingService = embeddingService;
        this.chromaService = chromaService;
    }

    public String ingestPdf(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            fileName = "unknown.pdf";
        }

        // 1. Extract entire text
        String text = pdfExtractionService.extractText(file);

        // 2. Semantic Chunking
        List<String> chunks = documentChunkingService.chunk(text);

        int count = 0;
        String fileId = UUID.randomUUID().toString();

        for (String chunk : chunks) {
            // 3. Embedding
            Map<String, Object> embeddingResponse = embeddingService.generateEmbedding(chunk);
            Object embedding = embeddingResponse.get("embedding");

            // 4. Metadata
            PdfMetadata metadataObj = new PdfMetadata(fileName, count);
            Map<String, Object> metadata = Map.of(
                    "fileName", metadataObj.getFileName(),
                    "chunkIndex", metadataObj.getChunkIndex()
            );

            String chunkId = fileId + "-chunk-" + count;

            // 5. Store in Chroma
            chromaService.addEmbedding(
                    CollectionType.PDF,
                    chunkId,
                    chunk,
                    embedding,
                    metadata
            );

            count++;
        }

        return "Stored " + count + " chunks successfully from " + fileName;
    }
}
