package com.sumit.youtubebuddy.service.rag.chroma;

import com.sumit.youtubebuddy.dto.rag.DocumentMetadata;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ChromaMetadataMapper {

    public DocumentMetadata map(
            Map<String, Object> metadata
    ) {
        if (metadata == null || metadata.isEmpty()) {
            return new DocumentMetadata();
        }

        DocumentMetadata docMetadata = new DocumentMetadata();
        
        String typeStr = (String) metadata.getOrDefault("sourceType", "");
        try {
            if (!typeStr.isEmpty()) {
                docMetadata.setSourceType(com.sumit.youtubebuddy.dto.rag.SourceType.valueOf(typeStr));
            }
        } catch (IllegalArgumentException e) {
            // Ignore if mapping fails
        }
        
        docMetadata.setSourceName((String) metadata.getOrDefault("sourceName", ""));
        docMetadata.setVideoTitle((String) metadata.getOrDefault("videoTitle", ""));
        docMetadata.setVideoId((String) metadata.getOrDefault("videoId", ""));
        docMetadata.setVideoUrl((String) metadata.getOrDefault("videoUrl", ""));
        docMetadata.setFileName((String) metadata.getOrDefault("fileName", ""));
        docMetadata.setPageTitle((String) metadata.getOrDefault("pageTitle", ""));
        docMetadata.setUrl((String) metadata.getOrDefault("url", ""));
        
        Object startMillisObj = metadata.get("startMillis");
        if (startMillisObj instanceof Number num) {
            docMetadata.setStartMillis(num.longValue());
        }
        
        Object endMillisObj = metadata.get("endMillis");
        if (endMillisObj instanceof Number num) {
            docMetadata.setEndMillis(num.longValue());
        }
        
        Object chunkIndexObj = metadata.get("chunkIndex");
        if (chunkIndexObj instanceof Number num) {
            docMetadata.setChunkIndex(num.intValue());
        }
        
        Object pageNumberObj = metadata.get("pageNumber");
        if (pageNumberObj instanceof Number num) {
            docMetadata.setPageNumber(num.intValue());
        }

        return docMetadata;
    }
}