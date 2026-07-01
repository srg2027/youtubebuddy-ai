package com.sumit.youtubebuddy.service;

import com.sumit.youtubebuddy.service.memory.ConversationMemoryService;
import com.sumit.youtubebuddy.service.rag.QueryRewriterService;
import com.sumit.youtubebuddy.service.rag.chroma.RetrieverService;
import dev.langchain4j.data.message.ChatMessage;
import org.springframework.stereotype.Service;
import com.sumit.youtubebuddy.service.prompt.PromptBuilderService;

import java.util.List;

@Service
public class RagChatService {

    private final RetrieverService retrieverService;
    private final GeminiService geminiService;
    private final ConversationMemoryService conversationMemoryService;
    private final PromptBuilderService promptBuilderService;
    private final QueryRewriterService queryRewriterService;

    public RagChatService(
            RetrieverService retrieverService,
            QueryRewriterService queryRewriterService,
            GeminiService geminiService,
            PromptBuilderService promptBuilderService,


            ConversationMemoryService conversationMemoryService
    ) {
        this.retrieverService = retrieverService;
        this.geminiService = geminiService;
        this.promptBuilderService = promptBuilderService;
        this.conversationMemoryService = conversationMemoryService;
        this.queryRewriterService = queryRewriterService;
    }

    public String askQuestion(String question) {

        // Store user's question
        conversationMemoryService.addUserMessage(question);

        // Build retrieval query
        List<ChatMessage> history =
                conversationMemoryService.getMessages();
        String retrievalQuery =
                queryRewriterService.rewrite(
                        history,
                        question
                );
        System.out.println("Rewritten Query: " + retrievalQuery);

        // Retrieve relevant context
        String context =
                retrieverService.retrieve(retrievalQuery);



        System.out.println("========== MEMORY ==========");
        System.out.println(history);
        System.out.println("============================");

        String prompt =
                promptBuilderService.buildRagPrompt(
                        history,
                        context,
                        question
                );

        // Generate answer
        System.out.println("========== QUESTION ==========");
        System.out.println(question);

        System.out.println("========== RETRIEVAL QUERY ==========");
        System.out.println(retrievalQuery);

        System.out.println("========== CONTEXT ==========");
        System.out.println(context);

        System.out.println("========== PROMPT ==========");
        System.out.println(prompt);
        String answer =
                geminiService.generateResponse(prompt);


        //conversationMemoryService.addUserMessage(question);
        conversationMemoryService.addAiMessage(answer);

        return answer;
    }


}