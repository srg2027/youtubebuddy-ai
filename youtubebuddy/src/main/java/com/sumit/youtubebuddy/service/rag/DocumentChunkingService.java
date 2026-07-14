package com.sumit.youtubebuddy.service.rag;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DocumentChunkingService {

    private static final int MAX_CHUNK_SIZE = 1000;

    /**
     * Splits full document text into semantic chunks (by sentences) up to MAX_CHUNK_SIZE.
     */
    public List<String> chunk(String documentText) {
        List<String> chunks = new ArrayList<>();
        if (documentText == null || documentText.trim().isEmpty()) {
            return chunks;
        }

        // Simple sentence boundary regex (looks for ., !, or ? followed by whitespace or end of string)
        Pattern pattern = Pattern.compile("[^.!?]+[.!?]+(?:\\s+|$)");
        Matcher matcher = pattern.matcher(documentText);

        StringBuilder currentChunk = new StringBuilder();

        while (matcher.find()) {
            String sentence = matcher.group().trim();
            if (sentence.isEmpty()) {
                continue;
            }

            if (currentChunk.length() + sentence.length() > MAX_CHUNK_SIZE && !currentChunk.isEmpty()) {
                chunks.add(currentChunk.toString().trim());
                currentChunk = new StringBuilder();
            }

            if (!currentChunk.isEmpty()) {
                currentChunk.append(" ");
            }
            currentChunk.append(sentence);
        }

        // Add any remaining text
        if (!currentChunk.isEmpty()) {
            chunks.add(currentChunk.toString().trim());
        }

        return chunks;
    }
}
