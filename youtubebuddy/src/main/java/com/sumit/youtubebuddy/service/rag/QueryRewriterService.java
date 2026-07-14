package com.sumit.youtubebuddy.service.rag;

import com.sumit.youtubebuddy.service.model.ModelRouterService;
import dev.langchain4j.data.message.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryRewriterService {

    private final ModelRouterService modelRouterService;

    public QueryRewriterService(
            ModelRouterService modelRouterService
    ) {
        this.modelRouterService = modelRouterService;
    }

    public String rewrite(
            List<ChatMessage> history,
            String question
    ) {

        StringBuilder conversation =
                new StringBuilder();

        for (ChatMessage message : history) {
            conversation
                    .append(message.toString())
                    .append("\n");
        }

        String prompt = """
                You are an expert query rewriting assistant.

                Your task is to rewrite the user's latest question into a
                complete standalone search query.

                Rules:

                - Use the previous conversation if needed.
                - Replace pronouns like "it", "they", "this", "that"
                  with the correct entity.
                - Preserve the original meaning.
                - Do NOT answer the question.
                - Output ONLY the rewritten query.

                Conversation:
                %s

                Current Question:
                %s

                Rewritten Query:
                """.formatted(
                conversation,
                question
        );

        return modelRouterService
                .generateResponse(prompt)
                .trim();
    }
}