package com.sumit.youtubebuddy.graph.workflow;

import com.sumit.youtubebuddy.graph.state.GraphState;
import org.springframework.stereotype.Component;

@Component
public class GraphWorkflow {

    private final GraphRunner graphRunner;

    public GraphWorkflow(GraphRunner graphRunner) {
        this.graphRunner = graphRunner;
    }
    
    public GraphState execute(GraphState initialState) {
        return graphRunner.run(initialState);
    }
}
