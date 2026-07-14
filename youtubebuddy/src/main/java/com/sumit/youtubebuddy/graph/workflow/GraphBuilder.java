package com.sumit.youtubebuddy.graph.workflow;

import com.sumit.youtubebuddy.graph.nodes.*;
import com.sumit.youtubebuddy.graph.state.GraphState;
import org.bsc.langgraph4j.StateGraph;
import org.bsc.langgraph4j.CompiledGraph;
import org.bsc.langgraph4j.action.EdgeAction;
import org.springframework.stereotype.Component;
import java.util.Map;

import static com.sumit.youtubebuddy.graph.workflow.GraphConstants.*;
import static org.bsc.langgraph4j.StateGraph.START;
import static org.bsc.langgraph4j.StateGraph.END;

@Component
public class GraphBuilder {

    private final LoadMemoryNode loadMemoryNode;
    private final QueryRewriteNode queryRewriteNode;
    private final PlannerNode plannerNode;
    private final RouterNode routerNode;
    private final ToolExecutorNode toolExecutorNode;
    private final RankerNode rankerNode;
    private final ContextBuilderNode contextBuilderNode;
    private final ReasoningNode reasoningNode;
    private final ReflectionNode reflectionNode;
    private final CitationNode citationNode;
    private final SaveMemoryNode saveMemoryNode;
    private final GraphExecutionTracker tracker;

    public GraphBuilder(
            LoadMemoryNode loadMemoryNode,
            QueryRewriteNode queryRewriteNode,
            PlannerNode plannerNode,
            RouterNode routerNode,
            ToolExecutorNode toolExecutorNode,
            RankerNode rankerNode,
            ContextBuilderNode contextBuilderNode,
            ReasoningNode reasoningNode,
            ReflectionNode reflectionNode,
            CitationNode citationNode,
            SaveMemoryNode saveMemoryNode,
            GraphExecutionTracker tracker
    ) {
        this.loadMemoryNode = loadMemoryNode;
        this.queryRewriteNode = queryRewriteNode;
        this.plannerNode = plannerNode;
        this.routerNode = routerNode;
        this.toolExecutorNode = toolExecutorNode;
        this.rankerNode = rankerNode;
        this.contextBuilderNode = contextBuilderNode;
        this.reasoningNode = reasoningNode;
        this.reflectionNode = reflectionNode;
        this.citationNode = citationNode;
        this.saveMemoryNode = saveMemoryNode;
        this.tracker = tracker;
    }

    public CompiledGraph<GraphState> build() {
        try {
            StateGraph<GraphState> graph = new StateGraph<>(new org.bsc.langgraph4j.serializer.StateSerializer<GraphState>(GraphState::new) {
                @Override
                public void write(GraphState t, java.io.ObjectOutput out) throws java.io.IOException {}
                @Override
                public GraphState read(java.io.ObjectInput in) throws java.io.IOException, ClassNotFoundException { return null; }
                @Override
                public GraphState cloneObject(GraphState state) {
                    return new GraphState(new java.util.HashMap<>(state.data()));
                }
            });

            // 1. Register Nodes
            graph.addNode(LOAD_MEMORY_NODE, org.bsc.langgraph4j.action.AsyncNodeAction.node_async(state -> { tracker.recordVisitedNode(state, LOAD_MEMORY_NODE); loadMemoryNode.execute(state); return state.toMap(); }));
            graph.addNode(QUERY_REWRITE_NODE, org.bsc.langgraph4j.action.AsyncNodeAction.node_async(state -> { tracker.recordVisitedNode(state, QUERY_REWRITE_NODE); queryRewriteNode.execute(state); return state.toMap(); }));
            graph.addNode(PLANNER_NODE, org.bsc.langgraph4j.action.AsyncNodeAction.node_async(state -> { tracker.recordVisitedNode(state, PLANNER_NODE); plannerNode.execute(state); return state.toMap(); }));
            graph.addNode(ROUTER_NODE, org.bsc.langgraph4j.action.AsyncNodeAction.node_async(state -> { tracker.recordVisitedNode(state, ROUTER_NODE); routerNode.execute(state); return state.toMap(); }));
            graph.addNode(TOOL_EXECUTOR_NODE, org.bsc.langgraph4j.action.AsyncNodeAction.node_async(state -> { tracker.recordVisitedNode(state, TOOL_EXECUTOR_NODE); toolExecutorNode.execute(state); return state.toMap(); }));
            graph.addNode(RANKER_NODE, org.bsc.langgraph4j.action.AsyncNodeAction.node_async(state -> { tracker.recordVisitedNode(state, RANKER_NODE); rankerNode.execute(state); return state.toMap(); }));
            graph.addNode(CONTEXT_BUILDER_NODE, org.bsc.langgraph4j.action.AsyncNodeAction.node_async(state -> { tracker.recordVisitedNode(state, CONTEXT_BUILDER_NODE); contextBuilderNode.execute(state); return state.toMap(); }));
            graph.addNode(REASONING_NODE, org.bsc.langgraph4j.action.AsyncNodeAction.node_async(state -> { tracker.recordVisitedNode(state, REASONING_NODE); reasoningNode.execute(state); return state.toMap(); }));
            graph.addNode(REFLECTION_NODE, org.bsc.langgraph4j.action.AsyncNodeAction.node_async(state -> { tracker.recordVisitedNode(state, REFLECTION_NODE); reflectionNode.execute(state); return state.toMap(); }));
            graph.addNode(CITATION_NODE, org.bsc.langgraph4j.action.AsyncNodeAction.node_async(state -> { tracker.recordVisitedNode(state, CITATION_NODE); citationNode.execute(state); return state.toMap(); }));
            graph.addNode(SAVE_MEMORY_NODE, org.bsc.langgraph4j.action.AsyncNodeAction.node_async(state -> { tracker.recordVisitedNode(state, SAVE_MEMORY_NODE); saveMemoryNode.execute(state); return state.toMap(); }));

            // 2. Register Edges (Linear flow before Reflection)
            graph.addEdge(START, LOAD_MEMORY_NODE);
            graph.addEdge(LOAD_MEMORY_NODE, QUERY_REWRITE_NODE);
            graph.addEdge(QUERY_REWRITE_NODE, PLANNER_NODE);
            graph.addEdge(PLANNER_NODE, ROUTER_NODE);
            graph.addEdge(ROUTER_NODE, TOOL_EXECUTOR_NODE);
            graph.addEdge(TOOL_EXECUTOR_NODE, RANKER_NODE);
            graph.addEdge(RANKER_NODE, CONTEXT_BUILDER_NODE);
            graph.addEdge(CONTEXT_BUILDER_NODE, REASONING_NODE);
            graph.addEdge(REASONING_NODE, REFLECTION_NODE);

            // 3. Conditional Routing (Reflection Logic)
            graph.addConditionalEdges(
                    REFLECTION_NODE,
                    org.bsc.langgraph4j.action.AsyncEdgeAction.edge_async(state -> {
                        return REFLECTION_PASS;
                    }),
                    Map.of(
                            REFLECTION_PASS, CITATION_NODE,
                            REFLECTION_FAIL, QUERY_REWRITE_NODE
                    )
            );

            // 4. Register Edges (Linear flow after Reflection)
            graph.addEdge(CITATION_NODE, SAVE_MEMORY_NODE);
            graph.addEdge(SAVE_MEMORY_NODE, END);

            return graph.compile();
        } catch (Exception e) {
            throw new RuntimeException("Failed to compile LangGraph", e);
        }
    }
}
