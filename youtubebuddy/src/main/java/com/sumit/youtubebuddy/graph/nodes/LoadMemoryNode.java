package com.sumit.youtubebuddy.graph.nodes;

import com.sumit.youtubebuddy.graph.state.GraphState;
import com.sumit.youtubebuddy.service.memory.ConversationMemoryService;
import org.springframework.stereotype.Component;

@Component
public class LoadMemoryNode {

    private final ConversationMemoryService conversationMemoryService;

    public LoadMemoryNode(ConversationMemoryService conversationMemoryService) {
        this.conversationMemoryService = conversationMemoryService;
    }

    public GraphState execute(GraphState state) {
        state.setConversationHistory(conversationMemoryService.getMessages());
        return state;
    }
}
