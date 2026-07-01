package com.sumit.youtubebuddy.service.rag;

import org.springframework.stereotype.Service;

@Service
public class AdaptiveRetrieverService {

    public int determineTopK(String question) {

        int words = question.trim().split("\\s+").length;

        // Very short factual questions
        if (words <= 4) {
            return 5;
        }

        // Medium questions
        if (words <= 10) {
            return 7;
        }

        // Long analytical questions
        return 10;
    }
}