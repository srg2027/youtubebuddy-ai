package com.sumit.youtubebuddy.service.rag.chroma;

import com.sumit.youtubebuddy.BaseIntegrationTest;
import com.sumit.youtubebuddy.dto.rag.RetrievalResult;
import com.sumit.youtubebuddy.service.rag.EmbeddingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class GenericRetrieverServiceTests extends BaseIntegrationTest {

    @Autowired
    private GenericRetrieverService genericRetrieverService;

    @MockitoBean
    private EmbeddingService embeddingService;

    @MockitoBean
    private ChromaService chromaService;

    @Test
    void testRetrieve_Success() {
        // Arrange
        String question = "What is Spring Boot?";
        List<Double> mockEmbedding = List.of(0.1, 0.2, 0.3);
        
        when(embeddingService.generateEmbedding(question))
                .thenReturn(Map.of("embedding", mockEmbedding));

        RetrievalResult mockResult = new RetrievalResult("Mock Context", Collections.emptyList());
        when(chromaService.search(eq(CollectionType.YOUTUBE), eq(mockEmbedding), eq(question)))
                .thenReturn(mockResult);

        // Act
        RetrievalResult result = genericRetrieverService.retrieve(CollectionType.YOUTUBE, question);

        // Assert
        assertNotNull(result);
        assertEquals("Mock Context", result.getContext());
        
        verify(embeddingService, times(1)).generateEmbedding(question);
        verify(chromaService, times(1)).search(CollectionType.YOUTUBE, mockEmbedding, question);
    }
}
