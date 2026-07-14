package com.sumit.youtubebuddy.graph.workflow;

public class GraphConstants {

    // Node Names
    public static final String START = "__START__";
    public static final String END = "__END__";
    public static final String LOAD_MEMORY_NODE = "LoadMemoryNode";
    public static final String QUERY_REWRITE_NODE = "QueryRewriteNode";
    public static final String PLANNER_NODE = "PlannerNode";
    public static final String ROUTER_NODE = "RouterNode";
    public static final String TOOL_EXECUTOR_NODE = "ToolExecutorNode";
    public static final String RANKER_NODE = "RankerNode";
    public static final String CONTEXT_BUILDER_NODE = "ContextBuilderNode";
    public static final String REASONING_NODE = "ReasoningNode";
    public static final String REFLECTION_NODE = "ReflectionNode";
    public static final String CITATION_NODE = "CitationNode";
    public static final String SAVE_MEMORY_NODE = "SaveMemoryNode";

    // Reflection Constants
    public static final int MAX_REFLECTION_RETRIES = 3;
    public static final String REFLECTION_PASS = "PASS";
    public static final String REFLECTION_FAIL = "FAIL";

    private GraphConstants() {
        // Prevent instantiation
    }
}
