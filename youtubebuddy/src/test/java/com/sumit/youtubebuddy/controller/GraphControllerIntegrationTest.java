package com.sumit.youtubebuddy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sumit.youtubebuddy.BaseIntegrationTest;
import com.sumit.youtubebuddy.dto.graph.GraphExecutionStatus;
import com.sumit.youtubebuddy.dto.graph.GraphRequest;
import com.sumit.youtubebuddy.dto.graph.GraphExecutionResponse;
import com.sumit.youtubebuddy.service.model.ModelRouterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

public class GraphControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private GraphController graphController;

    @Autowired
    private ObjectMapper objectMapper;

    @org.springframework.test.context.bean.override.mockito.MockitoBean
    private ModelRouterService modelRouterService;

    @BeforeEach
    void setupMock() {
        Mockito.when(modelRouterService.generateResponse(Mockito.anyString(), Mockito.any()))
                .thenReturn("Mocked LLM Response for Graph API");
        Mockito.when(modelRouterService.generateResponse(Mockito.anyString()))
                .thenReturn("Mocked LLM Response for Graph API");
    }

    @Test
    void testGraphChatEndpoint() throws Exception {
        GraphRequest request = GraphRequest.builder()
                .question("What is the main topic of this tutorial?")
                .modelName("gemini")
                .build();

        ResponseEntity<GraphExecutionResponse> responseEntity = graphController.chat(request);
        
        org.junit.jupiter.api.Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        GraphExecutionResponse response = responseEntity.getBody();
        
        org.junit.jupiter.api.Assertions.assertNotNull(response);
        org.junit.jupiter.api.Assertions.assertEquals("Mocked LLM Response for Graph API", response.getAnswer());
        org.junit.jupiter.api.Assertions.assertNotNull(response.getCitations());
        
        org.junit.jupiter.api.Assertions.assertNotNull(response.getMetadata());
        org.junit.jupiter.api.Assertions.assertEquals(GraphExecutionStatus.SUCCESS, response.getMetadata().getExecutionStatus());
        org.junit.jupiter.api.Assertions.assertTrue(response.getMetadata().getExecutionTime() >= 0);
        
        org.junit.jupiter.api.Assertions.assertNotNull(response.getMetadata().getVisitedNodes());
        org.junit.jupiter.api.Assertions.assertFalse(response.getMetadata().getVisitedNodes().isEmpty());
        org.junit.jupiter.api.Assertions.assertTrue(response.getMetadata().getVisitedNodes().contains("LoadMemoryNode"));
        org.junit.jupiter.api.Assertions.assertTrue(response.getMetadata().getVisitedNodes().contains("ReasoningNode"));
    }
}
