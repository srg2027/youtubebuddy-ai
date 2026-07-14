package com.sumit.youtubebuddy.dto.graph;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphExecutionMetadata {
    private GraphExecutionStatus executionStatus;
    private long executionTime;
    private List<String> visitedNodes;
    private int reflectionRetries;
    private List<String> toolsUsed;
}
