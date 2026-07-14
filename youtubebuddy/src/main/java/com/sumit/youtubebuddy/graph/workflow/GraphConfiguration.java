package com.sumit.youtubebuddy.graph.workflow;

import com.sumit.youtubebuddy.graph.state.GraphState;
import org.bsc.langgraph4j.CompiledGraph;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GraphConfiguration {
    
    @Bean
    public CompiledGraph<GraphState> compiledGraph(GraphBuilder graphBuilder) {
        return graphBuilder.build();
    }

    @Bean
    public GraphWorkflow graphWorkflow(GraphRunner graphRunner) {
        return new GraphWorkflow(graphRunner);
    }
}
