package com.sumit.youtubebuddy.graph.nodes;

import com.sumit.youtubebuddy.graph.state.GraphState;
import com.sumit.youtubebuddy.service.rag.hybrid.ContextMergerService;
import org.springframework.stereotype.Component;

@Component
public class ContextBuilderNode {

    private final ContextMergerService contextMergerService;

    public ContextBuilderNode(ContextMergerService contextMergerService) {
        this.contextMergerService = contextMergerService;
    }

    public GraphState execute(GraphState state) {
        com.sumit.youtubebuddy.dto.rag.RetrievalResult result = contextMergerService.mergeContext(state.getRankedChunks());
        state.setFinalContext(result);
        // Depending on contextMergerService, it also returns finalChunks but ranker already filtered them.
        return state;
    }
}
