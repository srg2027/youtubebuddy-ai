package com.sumit.youtubebuddy.graph.state;

import com.sumit.youtubebuddy.dto.chroma.ChromaResult;
import com.sumit.youtubebuddy.dto.rag.Citation;
import com.sumit.youtubebuddy.dto.rag.RetrievalResult;
import dev.langchain4j.data.message.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import org.bsc.langgraph4j.state.AgentState;

@Data
@EqualsAndHashCode(callSuper = true)
public class GraphState extends AgentState implements java.io.Serializable {

    public GraphState(Map<String, Object> initData) {
        super(initData);
        if (initData.containsKey("userQuestion")) this.userQuestion = (String) initData.get("userQuestion");
        if (initData.containsKey("modelName")) this.modelName = (String) initData.get("modelName");
        if (initData.containsKey("conversationHistory")) this.conversationHistory = (List<ChatMessage>) initData.get("conversationHistory");
        if (initData.containsKey("executionPlan")) this.executionPlan = (ExecutionPlan) initData.get("executionPlan");
        if (initData.containsKey("rewrittenQuery")) this.rewrittenQuery = (String) initData.get("rewrittenQuery");
        if (initData.containsKey("toolResults")) this.toolResults = (Map<String, ToolResult>) initData.get("toolResults");
        if (initData.containsKey("rankedChunks")) this.rankedChunks = (List<ChromaResult>) initData.get("rankedChunks");
        if (initData.containsKey("finalContext")) this.finalContext = (RetrievalResult) initData.get("finalContext");
        if (initData.containsKey("prompt")) this.prompt = (String) initData.get("prompt");
        if (initData.containsKey("llmResponse")) this.llmResponse = (String) initData.get("llmResponse");
        if (initData.containsKey("citations")) this.citations = (List<Citation>) initData.get("citations");
        if (initData.containsKey("reflectionResult")) this.reflectionResult = (ReflectionResult) initData.get("reflectionResult");
        if (initData.containsKey("reflectionRetryCount")) this.reflectionRetryCount = (Integer) initData.get("reflectionRetryCount");
        if (initData.containsKey("nodeStatus")) this.nodeStatus = (Map<String, NodeStatus>) initData.get("nodeStatus");
        if (initData.containsKey("timings")) this.timings = (Map<String, Long>) initData.get("timings");
        if (initData.containsKey("errors")) this.errors = (List<ErrorInfo>) initData.get("errors");
        if (initData.containsKey("visitedNodes")) this.visitedNodes = (List<String>) initData.get("visitedNodes");
        if (initData.containsKey("executionStartTime")) this.executionStartTime = (Long) initData.get("executionStartTime");
        if (initData.containsKey("executionEndTime")) this.executionEndTime = (Long) initData.get("executionEndTime");
        if (initData.containsKey("executionDuration")) this.executionDuration = (Long) initData.get("executionDuration");
        if (initData.containsKey("executedTools")) this.executedTools = (List<String>) initData.get("executedTools");
    }

    public GraphState() {
        super(new HashMap<>());
    }

    // Convert fields to map for GraphRunner
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        if (userQuestion != null) map.put("userQuestion", userQuestion);
        if (modelName != null) map.put("modelName", modelName);
        if (conversationHistory != null) map.put("conversationHistory", conversationHistory);
        if (executionPlan != null) map.put("executionPlan", executionPlan);
        if (rewrittenQuery != null) map.put("rewrittenQuery", rewrittenQuery);
        if (toolResults != null) map.put("toolResults", toolResults);
        if (rankedChunks != null) map.put("rankedChunks", rankedChunks);
        if (finalContext != null) map.put("finalContext", finalContext);
        if (prompt != null) map.put("prompt", prompt);
        if (llmResponse != null) map.put("llmResponse", llmResponse);
        if (citations != null) map.put("citations", citations);
        if (reflectionResult != null) map.put("reflectionResult", reflectionResult);
        map.put("reflectionRetryCount", reflectionRetryCount);
        if (nodeStatus != null) map.put("nodeStatus", nodeStatus);
        if (timings != null) map.put("timings", timings);
        if (errors != null) map.put("errors", errors);
        if (visitedNodes != null) map.put("visitedNodes", visitedNodes);
        map.put("executionStartTime", executionStartTime);
        map.put("executionEndTime", executionEndTime);
        map.put("executionDuration", executionDuration);
        if (executedTools != null) map.put("executedTools", executedTools);
        return map;
    }

    private String userQuestion;
    private String modelName;
    private List<ChatMessage> conversationHistory;
    private ExecutionPlan executionPlan;
    private String rewrittenQuery;
    private Map<String, ToolResult> toolResults;
    private List<ChromaResult> rankedChunks;
    private RetrievalResult finalContext;
    private String prompt;
    private String llmResponse;
    private List<Citation> citations;
    private ReflectionResult reflectionResult;
    private int reflectionRetryCount;
    private Map<String, NodeStatus> nodeStatus;
    private Map<String, Long> timings;
    private List<ErrorInfo> errors;
    private List<String> visitedNodes = new ArrayList<>();
    private long executionStartTime;
    private long executionEndTime;
    private long executionDuration;
    private List<String> executedTools = new ArrayList<>();
}
