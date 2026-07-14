package com.sumit.youtubebuddy.service.prompt;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoryFormatter {

    private static final int MAX_MESSAGES = 6;

    public String format(List<ChatMessage> history) {

        if (history == null || history.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        int start = Math.max(0, history.size() - MAX_MESSAGES);

        for (int i = start; i < history.size(); i++) {

            ChatMessage message = history.get(i);

            if (message instanceof UserMessage userMessage) {
                builder.append("User: ")
                        .append(userMessage.singleText());
            }
            else if (message instanceof AiMessage aiMessage) {
                builder.append("Assistant: ")
                        .append(aiMessage.text());
            }

            builder.append("\n");
        }

        return builder.toString();
    }
}