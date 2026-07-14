package com.sumit.youtubebuddy.graph.tools;

import com.sumit.youtubebuddy.graph.state.GraphState;
import com.sumit.youtubebuddy.graph.state.ToolResult;

public interface AgentTool {
    ToolType type();
    ToolResult execute(GraphState state);
}
