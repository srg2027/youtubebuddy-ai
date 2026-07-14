package com.sumit.youtubebuddy.service.rag;

import com.sumit.youtubebuddy.dto.chroma.ChromaResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitationFormatter {

    public List<com.sumit.youtubebuddy.dto.rag.Citation> format(List<ChromaResult> chunks) {
        
        List<com.sumit.youtubebuddy.dto.rag.Citation> citations = new java.util.ArrayList<>();
        
        if (chunks == null || chunks.isEmpty()) {
            return citations;
        }

        for (ChromaResult chunk : chunks) {
            com.sumit.youtubebuddy.dto.rag.DocumentMetadata metadata = chunk.getMetadata();
            if (metadata != null) {
                com.sumit.youtubebuddy.dto.rag.Citation citation = new com.sumit.youtubebuddy.dto.rag.Citation();
                citation.setSourceType(metadata.getSourceType());
                citation.setSourceName(metadata.getSourceName());
                citation.setChunkIndex(metadata.getChunkIndex());
                
                if (com.sumit.youtubebuddy.dto.rag.SourceType.WEBSITE.equals(metadata.getSourceType())) {
                    citation.setTitle(metadata.getPageTitle() != null && !metadata.getPageTitle().isEmpty() ? metadata.getPageTitle() : "Unknown");
                    citation.setUrl(metadata.getUrl());
                    citation.setDetails("Page: " + citation.getTitle() + ", URL: " + citation.getUrl());
                } else if (com.sumit.youtubebuddy.dto.rag.SourceType.PDF.equals(metadata.getSourceType()) || (metadata.getFileName() != null && !metadata.getFileName().isEmpty())) {
                    citation.setTitle(metadata.getFileName());
                    citation.setDetails("File: " + metadata.getFileName() + ", Index: " + metadata.getChunkIndex());
                } else {
                    citation.setTitle(metadata.getVideoTitle());
                    citation.setUrl(metadata.getVideoUrl());
                    citation.setDetails("Video: " + metadata.getVideoTitle());
                }
                
                citations.add(citation);
            }
        }
        
        return citations;
    }
}