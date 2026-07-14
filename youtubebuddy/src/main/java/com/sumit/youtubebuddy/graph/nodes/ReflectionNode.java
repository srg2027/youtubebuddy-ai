package com.sumit.youtubebuddy.graph.nodes;

import com.sumit.youtubebuddy.graph.state.GraphState;
import com.sumit.youtubebuddy.graph.state.ReflectionResult;
import org.springframework.stereotype.Component;

@Component
public class ReflectionNode {

    private final com.sumit.youtubebuddy.graph.workflow.GraphExecutionTracker tracker;

    public ReflectionNode(com.sumit.youtubebuddy.graph.workflow.GraphExecutionTracker tracker) {
        this.tracker = tracker;
    }

    public GraphState execute(GraphState state) {
        System.out.println("Executing ReflectionNode...");
        tracker.recordReflectionRetry(state);
        ReflectionResult result = new ReflectionResult();
        
        if (state.getLlmResponse() == null || state.getLlmResponse().isBlank()) {
            result.setRetryRequired(true);
            result.setReason("LLM response was blank.");
        } else {
            result.setPassed(true);
        }
        state.setReflectionResult(result);
        return state;
    }
}
