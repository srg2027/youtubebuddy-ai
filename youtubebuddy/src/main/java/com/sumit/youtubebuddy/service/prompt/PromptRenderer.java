package com.sumit.youtubebuddy.service.prompt;

import com.sumit.youtubebuddy.dto.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

@Service
public class PromptRenderer {

    public String render(PromptTemplate prompt) {

        StringBuilder builder = new StringBuilder();

        builder.append(prompt.getSystemPrompt())
                .append("\n\n");

        builder.append(prompt.getLanguageInstruction())
                .append("\n\n");

        builder.append(prompt.getHallucinationGuard())
                .append("\n\n");

        builder.append(prompt.getResponseFormatting())
                .append("\n\n");

        builder.append("Conversation History:\n")
                .append(prompt.getConversationHistory())
                .append("\n\n");

        builder.append(prompt.getRetrievedContext())
                .append("\n\n");

        builder.append("Current Question:\n")
                .append(prompt.getCurrentQuestion());

        return builder.toString();
    }
}