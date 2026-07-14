package com.sumit.youtubebuddy.service.rag.chroma;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumit.youtubebuddy.BaseIntegrationTest;
import com.sumit.youtubebuddy.dto.rag.RetrievalResult;
import com.sumit.youtubebuddy.service.rag.AdaptiveRetrieverService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class ChromaServiceTests extends BaseIntegrationTest {

    @Autowired
    private ChromaService chromaService;

    @Autowired
    private RestTemplate restTemplate;

    @MockitoBean
    private AdaptiveRetrieverService adaptiveRetrieverService;

    @MockitoBean
    private CollectionManagerService collectionManagerService;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void testSearch_GeneratesCorrectPayloadAndParsesResponse() {
        // Arrange
        String question = "Test question?";
        List<Double> embedding = List.of(0.1, 0.2);
        
        when(adaptiveRetrieverService.determineTopK(anyString())).thenReturn(5);
        when(collectionManagerService.getOrCreateCollectionId(CollectionType.YOUTUBE)).thenReturn("test-collection-id");

        String mockResponse = """
                {
                  "ids": [["id1"]],
                  "distances": [[0.15]],
                  "metadatas": [[{"videoTitle": "Spring Boot"}], []],
                  "documents": [["Spring Boot makes it easy."]]
                }
                """;

        String expectedUrl = "http://localhost:8000/api/v2/tenants/default_tenant/databases/default_database/collections/test-collection-id/query";

        mockServer.expect(requestTo(expectedUrl))
                .andExpect(method(HttpMethod.POST))
                .andExpect(jsonPath("$.n_results").value(5))
                .andExpect(jsonPath("$.include[0]").value("documents"))
                .andRespond(withSuccess(mockResponse, MediaType.APPLICATION_JSON));

        // Act
        RetrievalResult result = chromaService.search(CollectionType.YOUTUBE, embedding, question);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getChunks().size());
        assertEquals("Spring Boot makes it easy.", result.getChunks().get(0).getDocument());
        mockServer.verify();
    }
}
