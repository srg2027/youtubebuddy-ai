package com.sumit.youtubebuddy.service;

import com.sumit.youtubebuddy.dto.rag.ChatResponse;
import com.sumit.youtubebuddy.dto.rag.RetrievalResult;
import com.sumit.youtubebuddy.service.memory.ConversationMemoryService;
import com.sumit.youtubebuddy.service.model.ModelRouterService;
import com.sumit.youtubebuddy.service.prompt.PromptBuilderService;
import com.sumit.youtubebuddy.service.rag.CitationFormatter;
import com.sumit.youtubebuddy.service.rag.QueryRewriterService;
import com.sumit.youtubebuddy.service.rag.chroma.RetrieverService;
import dev.langchain4j.data.message.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagChatService {

    private final RetrieverService retrieverService;
    private final ModelRouterService modelRouterService;
    private final ConversationMemoryService conversationMemoryService;
    private final PromptBuilderService promptBuilderService;
    private final QueryRewriterService queryRewriterService;
    private final CitationFormatter citationFormatter;

    public RagChatService(
            RetrieverService retrieverService,
            QueryRewriterService queryRewriterService,
            ModelRouterService modelRouterService,
            PromptBuilderService promptBuilderService,
            ConversationMemoryService conversationMemoryService,
            CitationFormatter citationFormatter
    ) {
        this.retrieverService = retrieverService;
        this.queryRewriterService = queryRewriterService;
        this.modelRouterService = modelRouterService;
        this.promptBuilderService = promptBuilderService;
        this.conversationMemoryService = conversationMemoryService;
        this.citationFormatter = citationFormatter;
    }

    public ChatResponse askQuestion(
            String question,
            String modelName
    ) {

        // =====================================================
        // 1. Store current user message
        // =====================================================
        conversationMemoryService.addUserMessage(question);

        // =====================================================
        // 2. Get conversation history
        // =====================================================
        
        List<ChatMessage> history = conversationMemoryService.getMessages();
        
        String retrievalQuery = queryRewriterService.rewrite(history, question);
        
        RetrievalResult retrievalResult = retrieverService.retrieve(retrievalQuery);
        
        String context = retrievalResult.getContext();
        
        if (context == null || context.trim().isEmpty()) {
            String fallbackAnswer = "I could not find this information in the video.";
            conversationMemoryService.addAiMessage(fallbackAnswer);
            return new ChatResponse(fallbackAnswer, java.util.Collections.emptyList(), retrievalResult);
        }
        
        List<com.sumit.youtubebuddy.dto.rag.Citation> citations = citationFormatter.format(retrievalResult.getChunks());

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
        System.out.println("MODEL:");
        System.out.println(modelName == null ? "DEFAULT" : modelName);

        System.out.println("\nQUESTION:");
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
        // 6. Generate Answer
        // =====================================================
        String answer =
                modelRouterService.generateResponse(
                        prompt,
                        modelName
                );

        // =====================================================
        // 7. Store AI response
        // =====================================================
        conversationMemoryService.addAiMessage(answer);

        // =====================================================
        // 8. Return response
        // =====================================================
        return new ChatResponse(
                answer,
                citations,
                retrievalResult
        );
    }
}