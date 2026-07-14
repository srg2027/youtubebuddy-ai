package com.sumit.youtubebuddy.service.rag;

import com.sumit.youtubebuddy.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class EmbeddingServiceTests extends BaseIntegrationTest {

    @Autowired
    private EmbeddingService embeddingService;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void testGenerateEmbedding_APIRequestAndNormalization() {
        // Arrange
        String text = "Test text";
        // Raw vector: [3.0, 4.0] (length 5.0). Normalized should be [0.6, 0.8]
        String mockResponse = """
                {
                  "embedding": [3.0, 4.0]
                }
                """;

        mockServer.expect(requestTo("http://localhost:11434/api/embeddings"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(jsonPath("$.model").value("nomic-embed-text"))
                .andExpect(jsonPath("$.prompt").value(text))
                .andRespond(withSuccess(mockResponse, MediaType.APPLICATION_JSON));

        // Act
        Map<String, Object> result = embeddingService.generateEmbedding(text);

        // Assert
        assertNotNull(result);
        assertTrue(result.containsKey("embedding"));

        @SuppressWarnings("unchecked")
        List<Double> embedding = (List<Double>) result.get("embedding");

        assertEquals(2, embedding.size());
        assertEquals(0.6, embedding.get(0), 0.001);
        assertEquals(0.8, embedding.get(1), 0.001);
        
        mockServer.verify();
    }
}
