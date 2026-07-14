package com.sumit.youtubebuddy.graph.nodes;

import com.sumit.youtubebuddy.graph.state.GraphState;
import com.sumit.youtubebuddy.graph.tools.ToolRegistry;
import org.springframework.stereotype.Component;

@Component
public class ToolExecutorNode {

    private final ToolRegistry toolRegistry;
    private final com.sumit.youtubebuddy.graph.workflow.GraphExecutionTracker tracker;

    public ToolExecutorNode(ToolRegistry toolRegistry, com.sumit.youtubebuddy.graph.workflow.GraphExecutionTracker tracker) {
        this.toolRegistry = toolRegistry;
        this.tracker = tracker;
    }

    public GraphState execute(GraphState state) {
        java.util.Map<String, com.sumit.youtubebuddy.graph.state.ToolResult> results = new java.util.HashMap<>();
        if (state.getExecutionPlan() != null && state.getExecutionPlan().getRequiredTools() != null) {
            for (com.sumit.youtubebuddy.graph.tools.ToolType toolType : state.getExecutionPlan().getRequiredTools()) {
                com.sumit.youtubebuddy.graph.tools.AgentTool tool = toolRegistry.getTool(toolType);
                if (tool != null) {
                    tracker.recordExecutedTool(state, toolType.name());
                    results.put(toolType.name(), tool.execute(state));
                }
            }
        }
        state.setToolResults(results);
        return state;
    }
}
