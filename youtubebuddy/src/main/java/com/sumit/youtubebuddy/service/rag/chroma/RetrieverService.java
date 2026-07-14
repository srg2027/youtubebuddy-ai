package com.sumit.youtubebuddy.service.rag.chroma;

import org.springframework.stereotype.Service;
import com.sumit.youtubebuddy.dto.rag.RetrievalResult;

@Service
public class RetrieverService {

    private final GenericRetrieverService genericRetrieverService;

    public RetrieverService(GenericRetrieverService genericRetrieverService) {
        this.genericRetrieverService = genericRetrieverService;
    }

    public RetrievalResult retrieve(String question) {
        return genericRetrieverService.retrieve(CollectionType.YOUTUBE, question);
    }
}