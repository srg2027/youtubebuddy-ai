package com.sumit.youtubebuddy.service.prompt;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.UserMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromptBuilderService {
    public String buildRagPrompt(
            List<ChatMessage> history,
            String context,
            String question
    ) {

        String conversation =
                formatConversation(history);

        return """
        You are a helpful AI assistant.

        Use ONLY the provided context.

        If the answer is not available
        in the context, say:

        "I could not find this information
        in the video."

        Answer in the same language
        that the user asked the question,
        unless the user explicitly requests
        another language.

        Conversation History:
        %s

        Retrieved Context:
        %s

        Current Question:
        %s
        """
                .formatted(
                        conversation,
                        context,
                        question
                );
    }
    private String formatConversation(
            List<ChatMessage> history
    ) {

        StringBuilder builder =
                new StringBuilder();

        for (ChatMessage message : history) {

            builder.append(message.type())
                    .append(": ");

            if (message instanceof UserMessage userMessage) {
                builder.append(userMessage.singleText());
            } else if (message instanceof AiMessage aiMessage) {
                builder.append(aiMessage.text());
            } else {
                builder.append(message.toString());
            }

            builder.append("\n");
        }

        return builder.toString();
    }

}