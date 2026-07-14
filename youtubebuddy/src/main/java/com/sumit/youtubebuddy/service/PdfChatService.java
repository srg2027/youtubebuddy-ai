package com.sumit.youtubebuddy.service;

import com.sumit.youtubebuddy.dto.rag.ChatResponse;
import com.sumit.youtubebuddy.dto.rag.RetrievalResult;
import com.sumit.youtubebuddy.service.memory.ConversationMemoryService;
import com.sumit.youtubebuddy.service.model.ModelRouterService;
import com.sumit.youtubebuddy.service.prompt.PromptBuilderService;
import com.sumit.youtubebuddy.service.rag.CitationFormatter;
import com.sumit.youtubebuddy.service.rag.QueryRewriterService;
import com.sumit.youtubebuddy.service.rag.chroma.CollectionType;
import com.sumit.youtubebuddy.service.rag.chroma.GenericRetrieverService;
import dev.langchain4j.data.message.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PdfChatService {

    private final GenericRetrieverService genericRetrieverService;
    private final ModelRouterService modelRouterService;
    private final ConversationMemoryService conversationMemoryService;
    private final PromptBuilderService promptBuilderService;
    private final QueryRewriterService queryRewriterService;
    private final CitationFormatter citationFormatter;

    public PdfChatService(
            GenericRetrieverService genericRetrieverService,
            QueryRewriterService queryRewriterService,
            ModelRouterService modelRouterService,
            PromptBuilderService promptBuilderService,
            ConversationMemoryService conversationMemoryService,
            CitationFormatter citationFormatter
    ) {
        this.genericRetrieverService = genericRetrieverService;
        this.queryRewriterService = queryRewriterService;
        this.modelRouterService = modelRouterService;
        this.promptBuilderService = promptBuilderService;
        this.conversationMemoryService = conversationMemoryService;
        this.citationFormatter = citationFormatter;
    }

    public ChatResponse askQuestion(String question, String modelName) {
        
        conversationMemoryService.addUserMessage(question);
        
        List<ChatMessage> history = conversationMemoryService.getMessages();
        
        String retrievalQuery = queryRewriterService.rewrite(history, question);
        
        RetrievalResult retrievalResult = genericRetrieverService.retrieve(CollectionType.PDF, retrievalQuery);
        
        String context = retrievalResult.getContext();
        
        if (context == null || context.trim().isEmpty()) {
            String fallbackAnswer = "I could not find this information in the PDF document.";
            conversationMemoryService.addAiMessage(fallbackAnswer);
            return new ChatResponse(fallbackAnswer, java.util.Collections.emptyList(), retrievalResult);
        }
        
        List<com.sumit.youtubebuddy.dto.rag.Citation> citations = citationFormatter.format(retrievalResult.getChunks());

        // 5. Build RAG prompt
        String prompt = promptBuilderService.buildRagPrompt(history, context, question);

        System.out.println("\n==============================");
        System.out.println("PDF QA LOGS:");
        System.out.println("MODEL: " + (modelName == null ? "DEFAULT" : modelName));
        System.out.println("QUESTION: " + question);
        System.out.println("REWRITTEN QUERY: " + retrievalQuery);
        System.out.println("CONTEXT: " + context);
        System.out.println("PROMPT: " + prompt);
        System.out.println("CITATIONS: " + citations);
        System.out.println("==============================\n");

        // 6. Generate Answer
        String answer = modelRouterService.generateResponse(prompt, modelName);

        // 7. Store AI response
        conversationMemoryService.addAiMessage(answer);

        // 8. Return response
        return new ChatResponse(answer, citations, retrievalResult);
    }
}
