package com.sumit.youtubebuddy.graph.workflow;

import com.sumit.youtubebuddy.graph.state.GraphState;
import org.bsc.langgraph4j.CompiledGraph;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GraphRunner {

    private final CompiledGraph<GraphState> compiledGraph;
    private final GraphExecutionTracker tracker;

    public GraphRunner(CompiledGraph<GraphState> compiledGraph, GraphExecutionTracker tracker) {
        this.compiledGraph = compiledGraph;
        this.tracker = tracker;
    }

    public GraphState run(GraphState initialState) {
        if (initialState == null) {
            throw new IllegalArgumentException("GraphState cannot be null");
        }

        try {
            System.out.println("Entering node: START");
            System.out.println("Current GraphState summary: " + initialState);

            tracker.startExecution(initialState);
            tracker.recordVisitedNode(initialState, "START");

            // Using invoke to run the graph and retrieve the final state
            var finalStateOpt = compiledGraph.invoke(initialState.toMap());
            GraphState finalState = finalStateOpt.orElse(initialState);
            
            tracker.recordVisitedNode(finalState, "END");
            tracker.endExecution(finalState);
            
            System.out.println("Leaving node: END");
            System.out.println("Current GraphState summary: " + finalState);

            return finalState;
        } catch (Exception e) {
            throw new RuntimeException("Error executing graph", e);
        }
    }
}
