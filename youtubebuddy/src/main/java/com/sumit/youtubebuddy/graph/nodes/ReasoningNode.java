package com.sumit.youtubebuddy.graph.nodes;

import com.sumit.youtubebuddy.graph.state.GraphState;
import com.sumit.youtubebuddy.service.model.ModelRouterService;
import com.sumit.youtubebuddy.service.prompt.PromptBuilderService;
import org.springframework.stereotype.Component;

@Component
public class ReasoningNode {

    private final PromptBuilderService promptBuilderService;
    private final ModelRouterService modelRouterService;

    public ReasoningNode(
            PromptBuilderService promptBuilderService,
            ModelRouterService modelRouterService
    ) {
        this.promptBuilderService = promptBuilderService;
        this.modelRouterService = modelRouterService;
    }

    public GraphState execute(GraphState state) {
        String prompt = promptBuilderService.buildRagPrompt(
                state.getConversationHistory() != null ? state.getConversationHistory() : java.util.List.of(),
                state.getFinalContext() != null ? state.getFinalContext().getContext() : "",
                state.getUserQuestion()
        );
        state.setPrompt(prompt);
        String response = modelRouterService.generateResponse(prompt, state.getModelName());
        state.setLlmResponse(response);
        return state;
    }
}
