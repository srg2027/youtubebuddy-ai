package com.sumit.youtubebuddy.graph.nodes;

import com.sumit.youtubebuddy.graph.state.GraphState;
import com.sumit.youtubebuddy.service.memory.ConversationMemoryService;
import org.springframework.stereotype.Component;

@Component
public class SaveMemoryNode {

    private final ConversationMemoryService conversationMemoryService;

    public SaveMemoryNode(ConversationMemoryService conversationMemoryService) {
        this.conversationMemoryService = conversationMemoryService;
    }

    public GraphState execute(GraphState state) {
        if (state.getUserQuestion() != null && !state.getUserQuestion().isBlank()) {
            conversationMemoryService.addUserMessage(state.getUserQuestion());
        }
        if (state.getLlmResponse() != null && !state.getLlmResponse().isBlank()) {
            conversationMemoryService.addAiMessage(state.getLlmResponse());
        }
        return state;
    }
}
