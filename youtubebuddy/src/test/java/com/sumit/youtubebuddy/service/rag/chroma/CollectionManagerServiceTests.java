package com.sumit.youtubebuddy.service.rag.chroma;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumit.youtubebuddy.BaseIntegrationTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class CollectionManagerServiceTests extends BaseIntegrationTest {

    @Autowired
    private CollectionManagerService collectionManagerService;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        collectionManagerService.evict(CollectionType.YOUTUBE);
    }

    @Test
    void testGetOrCreateCollectionId_ExistingCollection() {
        // Arrange
        String mockResponse = """
                [
                  {
                    "name": "youtube_transcripts_v2",
                    "id": "12345-abcde",
                    "metadata": null,
                    "tenant": "default_tenant",
                    "database": "default_database"
                  }
                ]
                """;

        mockServer.expect(requestTo("http://localhost:8000/api/v2/tenants/default_tenant/databases/default_database/collections"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(mockResponse, MediaType.APPLICATION_JSON));

        // Act
        String collectionId = collectionManagerService.getOrCreateCollectionId(CollectionType.YOUTUBE);

        // Assert
        assertEquals("12345-abcde", collectionId);
        mockServer.verify();

        // Test cache hit
        String cachedId = collectionManagerService.getOrCreateCollectionId(CollectionType.YOUTUBE);
        assertEquals("12345-abcde", cachedId);
        // mockServer.verify() would fail if a second request was made, proving cache hit
    }

    @Test
    void testGetOrCreateCollectionId_NewCollectionCreation() {
        // Arrange
        String emptyResponse = "[]";
        String createResponse = """
                {
                  "name": "youtube_transcripts_v2",
                  "id": "new-uuid-67890",
                  "metadata": null,
                  "tenant": "default_tenant",
                  "database": "default_database"
                }
                """;

        mockServer.expect(requestTo("http://localhost:8000/api/v2/tenants/default_tenant/databases/default_database/collections"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(emptyResponse, MediaType.APPLICATION_JSON));

        mockServer.expect(requestTo("http://localhost:8000/api/v2/tenants/default_tenant/databases/default_database/collections"))
                .andExpect(method(HttpMethod.POST))
                .andExpect(jsonPath("$.name").value("youtube_transcripts_v2"))
                .andRespond(withSuccess(createResponse, MediaType.APPLICATION_JSON));

        // Act
        String collectionId = collectionManagerService.getOrCreateCollectionId(CollectionType.YOUTUBE);

        // Assert
        assertEquals("new-uuid-67890", collectionId);
        mockServer.verify();
    }
}
