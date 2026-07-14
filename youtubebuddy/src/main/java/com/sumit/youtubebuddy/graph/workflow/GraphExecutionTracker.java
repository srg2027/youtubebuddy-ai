package com.sumit.youtubebuddy.graph.workflow;

import com.sumit.youtubebuddy.graph.state.GraphState;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class GraphExecutionTracker {

    public void startExecution(GraphState state) {
        state.setExecutionStartTime(System.currentTimeMillis());
        if (state.getVisitedNodes() == null) {
            state.setVisitedNodes(new ArrayList<>());
        }
        if (state.getExecutedTools() == null) {
            state.setExecutedTools(new ArrayList<>());
        }
    }

    public void endExecution(GraphState state) {
        long endTime = System.currentTimeMillis();
        state.setExecutionEndTime(endTime);
        state.setExecutionDuration(endTime - state.getExecutionStartTime());
    }

    public void recordVisitedNode(GraphState state, String nodeName) {
        if (state.getVisitedNodes() == null) {
            state.setVisitedNodes(new ArrayList<>());
        }
        state.getVisitedNodes().add(nodeName);
    }

    public void recordExecutedTool(GraphState state, String toolName) {
        if (state.getExecutedTools() == null) {
            state.setExecutedTools(new ArrayList<>());
        }
        if (!state.getExecutedTools().contains(toolName)) {
             state.getExecutedTools().add(toolName);
        }
    }

    public void recordReflectionRetry(GraphState state) {
        state.setReflectionRetryCount(state.getReflectionRetryCount() + 1);
    }
}
