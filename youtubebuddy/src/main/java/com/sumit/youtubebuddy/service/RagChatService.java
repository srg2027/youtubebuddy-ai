package com.sumit.youtubebuddy.service;

import com.sumit.youtubebuddy.service.memory.ConversationMemoryService;
import com.sumit.youtubebuddy.service.prompt.PromptBuilderService;
import com.sumit.youtubebuddy.service.rag.QueryRewriterService;
import com.sumit.youtubebuddy.service.rag.chroma.RetrieverService;
import dev.langchain4j.data.message.ChatMessage;
import org.springframework.stereotype.Service;

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
        this.queryRewriterService = queryRewriterService;
        this.geminiService = geminiService;
        this.promptBuilderService = promptBuilderService;
        this.conversationMemoryService = conversationMemoryService;
    }

    public String askQuestion(String question) {

        // =====================================================
        // 1. Store current user message
        // =====================================================
        conversationMemoryService.addUserMessage(question);

        // =====================================================
        // 2. Get conversation history
        // =====================================================
        List<ChatMessage> history =
                conversationMemoryService.getMessages();

        // =====================================================
        // 3. Rewrite query for better retrieval
        // =====================================================
        String retrievalQuery =
                queryRewriterService.rewrite(
                        history,
                        question
                );

        // =====================================================
        // 4. Retrieve context from Chroma
        // =====================================================
        String context =
                retrieverService.retrieve(retrievalQuery);

        // =====================================================
        // 5. Build RAG prompt
        // =====================================================
        String prompt =
                promptBuilderService.buildRagPrompt(
                        history,
                        context,
                        question
                );

        // =====================================================
        // Debug Logs
        // =====================================================
        System.out.println("\n==============================");
        System.out.println("QUESTION:");
        System.out.println(question);

        System.out.println("\nREWRITTEN QUERY:");
        System.out.println(retrievalQuery);

        System.out.println("\nMEMORY:");
        history.forEach(System.out::println);

        System.out.println("\nCONTEXT:");
        System.out.println(context);

        System.out.println("\nPROMPT:");
        System.out.println(prompt);

        System.out.println("==============================\n");

        // =====================================================
        // 6. Generate answer
        // =====================================================
        String answer =
                geminiService.generateResponse(prompt);

        // =====================================================
        // 7. Store AI response
        // =====================================================
        conversationMemoryService.addAiMessage(answer);

        return answer;
    }
}