package com.sumit.youtubebuddy.service.rag.hybrid;

import com.sumit.youtubebuddy.dto.chroma.ChromaResult;
import com.sumit.youtubebuddy.dto.rag.DocumentMetadata;
import com.sumit.youtubebuddy.dto.rag.RetrievalResult;
import com.sumit.youtubebuddy.dto.rag.SourceType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContextMergerService {

    private static final int MAX_CONTEXT_LENGTH = 4000;

    public RetrievalResult mergeContext(List<ChromaResult> rankedResults) {
        if (rankedResults == null || rankedResults.isEmpty()) {
            return new RetrievalResult("", new ArrayList<>());
        }

        StringBuilder context = new StringBuilder();
        List<ChromaResult> finalChunks = new ArrayList<>();
        int chunkNumber = 1;

        for (ChromaResult result : rankedResults) {
            if (context.length() + result.getDocument().length() > MAX_CONTEXT_LENGTH) {
                break;
            }

            DocumentMetadata metadata = result.getMetadata();
            String sourceName = "Unknown";
            
            if (metadata != null && metadata.getSourceType() != null) {
                if (SourceType.YOUTUBE.equals(metadata.getSourceType())) {
                    sourceName = "YouTube (" + (metadata.getVideoTitle() != null ? metadata.getVideoTitle() : "") + ")";
                } else if (SourceType.PDF.equals(metadata.getSourceType())) {
                    sourceName = "PDF (" + (metadata.getFileName() != null ? metadata.getFileName() : "") + ")";
                } else if (SourceType.WEBSITE.equals(metadata.getSourceType())) {
                    sourceName = "Website (" + (metadata.getPageTitle() != null ? metadata.getPageTitle() : "") + ")";
                } else {
                    sourceName = metadata.getSourceType().name();
                }
            }

            context.append("Chunk ")
                    .append(chunkNumber++)
                    .append("\nSource: ")
                    .append(sourceName)
                    .append("\n...\n");

            context.append(result.getDocument())
                    .append("\n...\n");

            context.append("--------------------------------\n\n");
            
            finalChunks.add(result);
        }

        return new RetrievalResult(context.toString(), finalChunks);
    }
}
