package com.sumit.youtubebuddy.graph.nodes;

import com.sumit.youtubebuddy.graph.state.GraphState;
import com.sumit.youtubebuddy.graph.tools.ToolRegistry;
import org.springframework.stereotype.Component;

@Component
public class RouterNode {

    private final ToolRegistry toolRegistry;

    public RouterNode(ToolRegistry toolRegistry) {
        this.toolRegistry = toolRegistry;
    }

    public GraphState execute(GraphState state) {
        // Router resolves tools required by the ExecutionPlan.
        // It ensures ToolRegistry has the required tools.
        if (state.getExecutionPlan() != null && state.getExecutionPlan().getRequiredTools() != null) {
            for (var toolType : state.getExecutionPlan().getRequiredTools()) {
                if (!toolRegistry.exists(toolType)) {
                    throw new RuntimeException("Required tool not found in registry: " + toolType);
                }
            }
        }
        return state;
    }
}
