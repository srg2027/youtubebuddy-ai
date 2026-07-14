package com.sumit.youtubebuddy.service.rag.website;

import com.sumit.youtubebuddy.dto.rag.DocumentMetadata;
import com.sumit.youtubebuddy.dto.rag.ExtractedWebPage;
import com.sumit.youtubebuddy.dto.rag.IngestionResponse;
import com.sumit.youtubebuddy.dto.rag.SourceType;
import com.sumit.youtubebuddy.service.rag.DocumentChunkingService;
import com.sumit.youtubebuddy.service.rag.EmbeddingService;
import com.sumit.youtubebuddy.service.rag.chroma.ChromaService;
import com.sumit.youtubebuddy.service.rag.chroma.CollectionType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class WebsiteIngestionService {

    private final WebPageExtractionService webPageExtractionService;
    private final DocumentChunkingService documentChunkingService;
    private final EmbeddingService embeddingService;
    private final ChromaService chromaService;

    public WebsiteIngestionService(
            WebPageExtractionService webPageExtractionService,
            DocumentChunkingService documentChunkingService,
            EmbeddingService embeddingService,
            ChromaService chromaService
    ) {
        this.webPageExtractionService = webPageExtractionService;
        this.documentChunkingService = documentChunkingService;
        this.embeddingService = embeddingService;
        this.chromaService = chromaService;
    }

    public IngestionResponse ingestWebsite(String url) {
        // 1. Extract HTML
        ExtractedWebPage page = webPageExtractionService.extract(url);

        // 2. Semantic Chunking
        List<String> chunks = documentChunkingService.chunk(page.getText());

        int count = 0;
        String fileId = UUID.randomUUID().toString();

        for (String chunk : chunks) {
            // 3. Embedding
            Map<String, Object> embeddingResponse = embeddingService.generateEmbedding(chunk);
            Object embedding = embeddingResponse.get("embedding");

            // 4. Metadata
            DocumentMetadata metadata = new DocumentMetadata();
            metadata.setSourceType(SourceType.WEBSITE);
            metadata.setSourceName("Website");
            metadata.setPageTitle(page.getTitle());
            metadata.setUrl(url);
            metadata.setChunkIndex(count);

            String chunkId = fileId + "-chunk-" + count;

            // 5. Store in Chroma
            chromaService.addEmbedding(
                    CollectionType.WEBSITE,
                    chunkId,
                    chunk,
                    embedding,
                    metadata
            );

            count++;
        }

        return new IngestionResponse(
                "Website ingested successfully",
                page.getTitle(),
                count,
                CollectionType.WEBSITE.name()
        );
    }
}
