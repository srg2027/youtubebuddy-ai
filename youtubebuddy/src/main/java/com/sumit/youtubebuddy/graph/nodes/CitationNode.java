package com.sumit.youtubebuddy.graph.nodes;

import com.sumit.youtubebuddy.graph.state.GraphState;
import com.sumit.youtubebuddy.service.rag.CitationFormatter;
import org.springframework.stereotype.Component;

@Component
public class CitationNode {

    private final CitationFormatter citationFormatter;

    public CitationNode(CitationFormatter citationFormatter) {
        this.citationFormatter = citationFormatter;
    }

    public GraphState execute(GraphState state) {
        if (state.getRankedChunks() != null) {
            state.setCitations(citationFormatter.format(state.getRankedChunks()));
        }
        return state;
    }
}
