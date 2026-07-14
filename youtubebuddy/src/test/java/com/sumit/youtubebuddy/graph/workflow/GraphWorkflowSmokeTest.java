package com.sumit.youtubebuddy.graph.workflow;

import com.sumit.youtubebuddy.BaseIntegrationTest;
import com.sumit.youtubebuddy.graph.state.GraphState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(properties = "spring.profiles.active=test")
public class GraphWorkflowSmokeTest extends BaseIntegrationTest {

    @Autowired
    private GraphRunner graphRunner;

    @org.springframework.test.context.bean.override.mockito.MockitoBean
    private com.sumit.youtubebuddy.service.model.ModelRouterService modelRouterService;

    @org.junit.jupiter.api.BeforeEach
    void setupMock() {
        org.mockito.Mockito.when(modelRouterService.generateResponse(org.mockito.Mockito.anyString(), org.mockito.Mockito.any()))
                .thenReturn("Mocked LLM Response");
        org.mockito.Mockito.when(modelRouterService.generateResponse(org.mockito.Mockito.anyString()))
                .thenReturn("Mocked LLM Response");
    }

    @Test
    public void testGraphExecutesWithoutExceptions() {
        // Arrange
        GraphState dummyState = new GraphState();
        dummyState.setUserQuestion("How does LangGraph work?");
        dummyState.setModelName("gemini");

        // Act
        GraphState finalState = graphRunner.run(dummyState);

        // Assert
        assertNotNull(finalState, "Final state should not be null");
        assertEquals("How does LangGraph work?", finalState.getUserQuestion());
        assertEquals("gemini", finalState.getModelName());
        
        System.out.println("Smoke test passed: Graph executed from START to END successfully.");
    }
}
