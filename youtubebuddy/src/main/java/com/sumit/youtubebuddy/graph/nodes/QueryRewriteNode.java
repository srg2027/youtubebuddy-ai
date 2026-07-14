package com.sumit.youtubebuddy.graph.nodes;

import com.sumit.youtubebuddy.graph.state.GraphState;
import com.sumit.youtubebuddy.service.rag.QueryRewriterService;
import org.springframework.stereotype.Component;

@Component
public class QueryRewriteNode {

    private final QueryRewriterService queryRewriterService;

    public QueryRewriteNode(QueryRewriterService queryRewriterService) {
        this.queryRewriterService = queryRewriterService;
    }

    public GraphState execute(GraphState state) {
        String rewritten = queryRewriterService.rewrite(
                state.getConversationHistory() != null ? state.getConversationHistory() : java.util.List.of(),
                state.getUserQuestion()
        );
        state.setRewrittenQuery(rewritten);
        return state;
    }
}
