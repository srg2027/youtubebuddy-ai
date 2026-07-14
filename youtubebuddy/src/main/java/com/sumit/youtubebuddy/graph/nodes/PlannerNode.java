package com.sumit.youtubebuddy.graph.nodes;

import com.sumit.youtubebuddy.graph.state.GraphState;
import com.sumit.youtubebuddy.graph.state.ExecutionPlan;
import com.sumit.youtubebuddy.graph.tools.ToolType;
import com.sumit.youtubebuddy.service.rag.chroma.CollectionType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PlannerNode {

    public PlannerNode() {
    }

    public GraphState execute(GraphState state) {
        ExecutionPlan plan = ExecutionPlan.builder()
                .requiredTools(List.of(ToolType.HYBRID_SEARCH))
                .selectedSources(List.of(CollectionType.YOUTUBE, CollectionType.PDF, CollectionType.WEBSITE))
                .reflectionEnabled(true)
                .citationEnabled(true)
                .parallelExecutionAllowed(false)
                .priority("HIGH")
                .reasoningMode("STANDARD")
                .build();
        state.setExecutionPlan(plan);
        return state;
    }
}
