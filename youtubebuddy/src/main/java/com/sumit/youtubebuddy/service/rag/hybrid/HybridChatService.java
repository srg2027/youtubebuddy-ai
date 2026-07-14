package com.sumit.youtubebuddy.service.rag.hybrid;

import com.sumit.youtubebuddy.dto.chroma.ChromaResult;
import com.sumit.youtubebuddy.dto.rag.ChatResponse;
import com.sumit.youtubebuddy.dto.rag.RetrievalResult;
import com.sumit.youtubebuddy.service.memory.ConversationMemoryService;
import com.sumit.youtubebuddy.service.model.ModelRouterService;
import com.sumit.youtubebuddy.service.prompt.PromptBuilderService;
import com.sumit.youtubebuddy.service.rag.CitationFormatter;
import com.sumit.youtubebuddy.service.rag.QueryRewriterService;
import dev.langchain4j.data.message.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HybridChatService {

    private final HybridRetrieverService hybridRetrieverService;
    private final RetrievalRankerService retrievalRankerService;
    private final ContextMergerService contextMergerService;
    private final ModelRouterService modelRouterService;
    private final ConversationMemoryService conversationMemoryService;
    private final PromptBuilderService promptBuilderService;
    private final QueryRewriterService queryRewriterService;
    private final CitationFormatter citationFormatter;

    public HybridChatService(
            HybridRetrieverService hybridRetrieverService,
            RetrievalRankerService retrievalRankerService,
            ContextMergerService contextMergerService,
            QueryRewriterService queryRewriterService,
            ModelRouterService modelRouterService,
            PromptBuilderService promptBuilderService,
            ConversationMemoryService conversationMemoryService,
            CitationFormatter citationFormatter
    ) {
        this.hybridRetrieverService = hybridRetrieverService;
        this.retrievalRankerService = retrievalRankerService;
        this.contextMergerService = contextMergerService;
        this.queryRewriterService = queryRewriterService;
        this.modelRouterService = modelRouterService;
        this.promptBuilderService = promptBuilderService;
        this.conversationMemoryService = conversationMemoryService;
        this.citationFormatter = citationFormatter;
    }

    public ChatResponse askQuestion(String question, String modelName) {
        
        // 1. Store user message
        conversationMemoryService.addUserMessage(question);

        // 2. Conversation memory
        List<ChatMessage> history = conversationMemoryService.getMessages();

        // 3. Query rewrite
        String retrievalQuery = queryRewriterService.rewrite(history, question);

        // 4. Hybrid retrieval (from all supported collections)
        List<ChromaResult> rawResults = hybridRetrieverService.retrieveHybrid(retrievalQuery);

        // 5. Rank & Filter
        List<ChromaResult> rankedResults = retrievalRankerService.rankAndFilter(rawResults);

        // 6. Context Merging
        RetrievalResult retrievalResult = contextMergerService.mergeContext(rankedResults);
        String context = retrievalResult.getContext();

        // Fallback
        if (context == null || context.trim().isEmpty()) {
            String fallbackAnswer = "I could not find this information in any of the ingested sources.";
            conversationMemoryService.addAiMessage(fallbackAnswer);
            return new ChatResponse(fallbackAnswer, java.util.Collections.emptyList(), retrievalResult);
        }

        // Citations
        List<com.sumit.youtubebuddy.dto.rag.Citation> citations = citationFormatter.format(retrievalResult.getChunks());

        // 7. Prompt Building
        String prompt = promptBuilderService.buildRagPrompt(history, context, question);

        System.out.println("\n==============================");
        System.out.println("HYBRID RAG LOGS:");
        System.out.println("MODEL: " + (modelName == null ? "DEFAULT" : modelName));
        System.out.println("QUESTION: " + question);
        System.out.println("REWRITTEN QUERY: " + retrievalQuery);
        System.out.println("CONTEXT: \n" + context);
        System.out.println("CITATIONS: " + citations.size() + " citations found across multiple sources");
        System.out.println("==============================\n");

        // 8. Model Routing
        String answer = modelRouterService.generateResponse(prompt, modelName);

        // 9. Store AI response
        conversationMemoryService.addAiMessage(answer);

        // 10. Return Response
        return new ChatResponse(answer, citations, retrievalResult);
    }
}
