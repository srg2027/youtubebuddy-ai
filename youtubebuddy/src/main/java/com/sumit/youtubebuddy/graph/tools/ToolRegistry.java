package com.sumit.youtubebuddy.graph.tools;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ToolRegistry {

    private final Map<ToolType, AgentTool> tools;

    public ToolRegistry(Collection<AgentTool> agentTools) {
        this.tools = agentTools.stream()
                .collect(Collectors.toMap(AgentTool::type, tool -> tool));
    }

    public AgentTool getTool(ToolType type) {
        return tools.get(type);
    }

    public Collection<AgentTool> getAllTools() {
        return tools.values();
    }

    public boolean exists(ToolType type) {
        return tools.containsKey(type);
    }
}
