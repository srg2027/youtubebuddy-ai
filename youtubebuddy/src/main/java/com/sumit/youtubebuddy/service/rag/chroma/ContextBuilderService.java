package com.sumit.youtubebuddy.service.rag.chroma;

import com.sumit.youtubebuddy.dto.chroma.ChromaResult;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class ContextBuilderService {

    private static final double MAX_DISTANCE = 1.5;
    private static final int MAX_CONTEXT_LENGTH = 4000;

    public String buildContext(List<ChromaResult> results) {

        if (results == null || results.isEmpty()) {
            return "";
        }

        Set<String> uniqueChunks = new LinkedHashSet<>();

        StringBuilder context = new StringBuilder();

        int chunkNumber = 1;

        for (ChromaResult result : results) {

            // Distance Filtering
            if (result.getDistance() > MAX_DISTANCE) {
                continue;
            }

            // Duplicate Removal
            if (!uniqueChunks.add(result.getDocument())) {
                continue;
            }
            if (context.length() + result.getDocument().length() > MAX_CONTEXT_LENGTH) {
                break;
            }

            context.append("Chunk ")
                    .append(chunkNumber++)
                    .append(":\n");

            context.append(result.getDocument())
                    .append("\n\n");

            context.append("--------------------------------\n\n");
        }

        return context.toString();
    }
}