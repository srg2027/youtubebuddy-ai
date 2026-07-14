package com.sumit.youtubebuddy.service.rag.hybrid;

import com.sumit.youtubebuddy.dto.chroma.ChromaResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
public class RetrievalRankerService {

    private static final double MAX_DISTANCE = 1.5;

    public List<ChromaResult> rankAndFilter(List<ChromaResult> results) {
        if (results == null || results.isEmpty()) {
            return new ArrayList<>();
        }

        // 1. Sort globally by distance ascending (lower distance = better semantic match)
        results.sort(Comparator.comparingDouble(ChromaResult::getDistance));

        // 2. Filter by max distance and deduplicate by document content
        List<ChromaResult> rankedResults = new ArrayList<>();
        Set<String> uniqueChunks = new LinkedHashSet<>();

        for (ChromaResult result : results) {
            if (result.getDistance() > MAX_DISTANCE) {
                continue;
            }

            if (uniqueChunks.add(result.getDocument())) {
                rankedResults.add(result);
            }
        }

        return rankedResults;
    }
}
