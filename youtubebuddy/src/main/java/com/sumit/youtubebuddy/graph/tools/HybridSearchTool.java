package com.sumit.youtubebuddy.graph.tools;

import com.sumit.youtubebuddy.graph.state.GraphState;
import com.sumit.youtubebuddy.graph.state.ToolResult;
import com.sumit.youtubebuddy.service.rag.hybrid.HybridRetrieverService;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class HybridSearchTool implements AgentTool {

    private final HybridRetrieverService hybridRetrieverService;

    public HybridSearchTool(HybridRetrieverService hybridRetrieverService) {
        this.hybridRetrieverService = hybridRetrieverService;
    }

    @Override
    public ToolType type() {
        return ToolType.HYBRID_SEARCH;
    }

    @Override
    public ToolResult execute(GraphState state) {
        var chunks = hybridRetrieverService.retrieveHybrid(state.getRewrittenQuery() != null ? state.getRewrittenQuery() : state.getUserQuestion());
        
        ToolResult result = new ToolResult();
        result.setToolName(type().name());
        result.setStatus("SUCCESS");
        
        Map<String, Object> payload = new HashMap<>();
        payload.put("chunks", chunks);
        result.setPayload(payload);
        
        return result;
    }
}
