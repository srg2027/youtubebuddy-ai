package com.sumit.youtubebuddy.service.rag.hybrid;

import com.sumit.youtubebuddy.dto.chroma.ChromaResult;
import com.sumit.youtubebuddy.service.rag.chroma.CollectionType;
import com.sumit.youtubebuddy.service.rag.chroma.GenericRetrieverService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HybridRetrieverService {

    private final GenericRetrieverService genericRetrieverService;
    
    // Configurable list of supported collections
    private final List<CollectionType> supportedCollections = List.of(
            CollectionType.YOUTUBE,
            CollectionType.PDF,
            CollectionType.WEBSITE
    );

    public HybridRetrieverService(GenericRetrieverService genericRetrieverService) {
        this.genericRetrieverService = genericRetrieverService;
    }

    public List<ChromaResult> retrieveHybrid(String question) {
        List<ChromaResult> allResults = new ArrayList<>();

        for (CollectionType type : supportedCollections) {
            try {
                // retrieve() returns RetrievalResult containing List<ChromaResult> chunks
                List<ChromaResult> chunks = genericRetrieverService.retrieve(type, question).getChunks();
                if (chunks != null) {
                    allResults.addAll(chunks);
                }
            } catch (Exception e) {
                System.err.println("Failed to retrieve from collection: " + type.name());
                e.printStackTrace();
            }
        }

        return allResults;
    }
}
