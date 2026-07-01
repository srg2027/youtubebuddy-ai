package com.sumit.youtubebuddy.service.memory;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationMemoryService {

    private final ChatMemory chatMemory;

    public ConversationMemoryService(ChatMemory chatMemory) {
        this.chatMemory = chatMemory;
    }
    public void addUserMessage(String question) {

        chatMemory.add(
                UserMessage.from(question)
        );

    }
    public void addAiMessage(String answer) {

        chatMemory.add(
                AiMessage.from(answer)
        );

    }
    public List<ChatMessage> getMessages() {

        return chatMemory.messages();

    }
    public void clear() {

        chatMemory.clear();

    }

}