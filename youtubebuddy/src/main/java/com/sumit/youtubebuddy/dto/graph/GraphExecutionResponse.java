package com.sumit.youtubebuddy.dto.graph;

import com.sumit.youtubebuddy.dto.rag.Citation;
import com.sumit.youtubebuddy.graph.state.ErrorInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphExecutionResponse {
    private String answer;
    private List<Citation> citations;
    private GraphExecutionMetadata metadata;
    private List<ErrorInfo> errors;
}
