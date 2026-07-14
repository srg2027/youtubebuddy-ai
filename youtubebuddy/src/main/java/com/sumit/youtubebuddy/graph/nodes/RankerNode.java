package com.sumit.youtubebuddy.graph.nodes;

import com.sumit.youtubebuddy.graph.state.GraphState;
import com.sumit.youtubebuddy.service.rag.hybrid.RetrievalRankerService;
import org.springframework.stereotype.Component;

@Component
public class RankerNode {

    private final RetrievalRankerService retrievalRankerService;

    public RankerNode(RetrievalRankerService retrievalRankerService) {
        this.retrievalRankerService = retrievalRankerService;
    }

    public GraphState execute(GraphState state) {
        java.util.List<com.sumit.youtubebuddy.dto.chroma.ChromaResult> allChunks = new java.util.ArrayList<>();
        if (state.getToolResults() != null) {
            for (com.sumit.youtubebuddy.graph.state.ToolResult result : state.getToolResults().values()) {
                if (result.getPayload() != null && result.getPayload() instanceof java.util.Map) {
                    java.util.Map<?, ?> payloadMap = (java.util.Map<?, ?>) result.getPayload();
                    if (payloadMap.containsKey("chunks")) {
                        @SuppressWarnings("unchecked")
                        java.util.List<com.sumit.youtubebuddy.dto.chroma.ChromaResult> chunks =
                                (java.util.List<com.sumit.youtubebuddy.dto.chroma.ChromaResult>) payloadMap.get("chunks");
                        allChunks.addAll(chunks);
                    }
                }
            }
        }
        state.setRankedChunks(retrievalRankerService.rankAndFilter(allChunks));
        return state;
    }
}
