package com.sumit.youtubebuddy.graph.workflow;

import com.sumit.youtubebuddy.BaseIntegrationTest;
import com.sumit.youtubebuddy.graph.state.GraphState;
import com.sumit.youtubebuddy.graph.state.NodeStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.sumit.youtubebuddy.service.model.ModelRouterService;
import org.mockito.Mockito;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class GraphWorkflowIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private GraphRunner graphRunner;

    @MockitoBean
    private ModelRouterService modelRouterService;

    @BeforeEach
    void setupMock() {
        Mockito.when(modelRouterService.generateResponse(Mockito.anyString(), Mockito.any()))
                .thenReturn("Mocked LLM Response");
        Mockito.when(modelRouterService.generateResponse(Mockito.anyString()))
                .thenReturn("Mocked LLM Response");
    }

    @Test
    void testFullGraphExecutionWithRealServices() {
        GraphState initialState = new GraphState();
        initialState.setUserQuestion("What is the main topic of this tutorial?");
        initialState.setModelName("gemini"); // Use the mocked gemini from integration tests

        GraphState finalState = graphRunner.run(initialState);

        // 1. Memory Node
        assertNotNull(finalState.getConversationHistory(), "Conversation history should be loaded");

        // 2. Query Rewrite Node
        assertNotNull(finalState.getRewrittenQuery(), "Query should be rewritten");
        assertFalse(finalState.getRewrittenQuery().isBlank());

        // 3. Planner Node
        assertNotNull(finalState.getExecutionPlan(), "Execution plan should be generated");
        assertTrue(finalState.getExecutionPlan().getRequiredTools().size() > 0, "Should have required tools");

        // 4. Tool Executor Node
        assertNotNull(finalState.getToolResults(), "Tool results should be present");
        assertTrue(finalState.getToolResults().containsKey("HYBRID_SEARCH"), "Hybrid search tool should have executed");
        assertEquals("SUCCESS", finalState.getToolResults().get("HYBRID_SEARCH").getStatus());

        // 5. Ranker Node
        assertNotNull(finalState.getRankedChunks(), "Chunks should be ranked");

        // 6. Context Builder Node
        assertNotNull(finalState.getFinalContext(), "Final context should be built");
        assertNotNull(finalState.getFinalContext().getContext(), "Context string should be present");

        // 7. Reasoning Node
        assertNotNull(finalState.getPrompt(), "Prompt should be generated");
        assertNotNull(finalState.getLlmResponse(), "LLM response should be generated");
        assertFalse(finalState.getLlmResponse().isBlank());

        // 8. Citation Node
        assertNotNull(finalState.getCitations(), "Citations should be formatted");

        // 9. NodeStatus mapping checks (optional but useful if implemented later)
        // Check that node execution didn't produce nulls
        System.out.println("Integration test passed successfully. Final LLM Response: " + finalState.getLlmResponse());
    }
}
