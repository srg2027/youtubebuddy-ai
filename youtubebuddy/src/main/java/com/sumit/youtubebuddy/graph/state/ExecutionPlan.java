package com.sumit.youtubebuddy.graph.state;

import com.sumit.youtubebuddy.service.rag.chroma.CollectionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import com.sumit.youtubebuddy.graph.tools.ToolType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionPlan implements java.io.Serializable {
    private List<CollectionType> selectedSources;
    private List<ToolType> requiredTools;
    private String priority;
    private String reasoningMode;
    private boolean reflectionEnabled;
    private boolean citationEnabled;
    private boolean parallelExecutionAllowed;
    private List<String> futureAgentTasks;
}
