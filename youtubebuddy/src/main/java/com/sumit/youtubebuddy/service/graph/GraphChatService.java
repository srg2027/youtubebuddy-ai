package com.sumit.youtubebuddy.service.graph;

import com.sumit.youtubebuddy.dto.graph.GraphExecutionMetadata;
import com.sumit.youtubebuddy.dto.graph.GraphExecutionResponse;
import com.sumit.youtubebuddy.dto.graph.GraphExecutionStatus;
import com.sumit.youtubebuddy.dto.graph.GraphRequest;
import com.sumit.youtubebuddy.graph.state.GraphState;
import com.sumit.youtubebuddy.graph.workflow.GraphRunner;
import org.springframework.stereotype.Service;

@Service
public class GraphChatService {

    private final GraphRunner graphRunner;

    public GraphChatService(GraphRunner graphRunner) {
        this.graphRunner = graphRunner;
    }

    public GraphExecutionResponse executeChat(GraphRequest request) {
        GraphState initialState = new GraphState();
        initialState.setUserQuestion(request.getQuestion());
        initialState.setModelName(request.getModelName());

        GraphState finalState = graphRunner.run(initialState);

        return buildResponse(finalState);
    }

    private GraphExecutionResponse buildResponse(GraphState state) {
        GraphExecutionStatus status = GraphExecutionStatus.SUCCESS;
        
        if (state.getErrors() != null && !state.getErrors().isEmpty()) {
            if (state.getLlmResponse() == null || state.getLlmResponse().isBlank()) {
                status = GraphExecutionStatus.FAILED;
            } else {
                status = GraphExecutionStatus.PARTIAL_SUCCESS;
            }
        }

        GraphExecutionMetadata metadata = GraphExecutionMetadata.builder()
                .executionStatus(status)
                .executionTime(state.getExecutionDuration())
                .visitedNodes(state.getVisitedNodes())
                .reflectionRetries(state.getReflectionRetryCount())
                .toolsUsed(state.getExecutedTools())
                .build();

        return GraphExecutionResponse.builder()
                .answer(state.getLlmResponse())
                .citations(state.getCitations())
                .metadata(metadata)
                .errors(state.getErrors())
                .build();
    }
}
