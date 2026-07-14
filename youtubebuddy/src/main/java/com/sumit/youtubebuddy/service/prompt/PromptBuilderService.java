package com.sumit.youtubebuddy.service.prompt;

import com.sumit.youtubebuddy.dto.prompt.PromptTemplate;
import dev.langchain4j.data.message.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromptBuilderService {

    private final SystemPromptService systemPromptService;
    private final LanguageInstructionService languageInstructionService;
    private final MemoryFormatter memoryFormatter;
    private final ContextFormatter contextFormatter;
    private final HallucinationGuardService hallucinationGuardService;
    private final ResponseFormatterService responseFormatterService;
    private final PromptRenderer promptRenderer;

    public PromptBuilderService(
            SystemPromptService systemPromptService,
            LanguageInstructionService languageInstructionService,
            MemoryFormatter memoryFormatter,
            ContextFormatter contextFormatter,
            HallucinationGuardService hallucinationGuardService,
            ResponseFormatterService responseFormatterService,
            PromptRenderer promptRenderer
    ) {
        this.systemPromptService = systemPromptService;
        this.languageInstructionService = languageInstructionService;
        this.memoryFormatter = memoryFormatter;
        this.contextFormatter = contextFormatter;
        this.hallucinationGuardService = hallucinationGuardService;
        this.responseFormatterService = responseFormatterService;
        this.promptRenderer = promptRenderer;
    }

    public String buildRagPrompt(
            List<ChatMessage> history,
            String context,
            String question
    ) {

        PromptTemplate promptTemplate = new PromptTemplate();

        promptTemplate.setSystemPrompt(
                systemPromptService.getSystemPrompt()
        );

        promptTemplate.setLanguageInstruction(
                languageInstructionService.buildLanguageInstruction(question)
        );

        promptTemplate.setHallucinationGuard(
                hallucinationGuardService.getInstructions()
        );

        promptTemplate.setResponseFormatting(
                responseFormatterService.getInstructions()
        );

        promptTemplate.setConversationHistory(
                memoryFormatter.format(history)
        );

        promptTemplate.setRetrievedContext(
                contextFormatter.format(context)
        );

        promptTemplate.setCurrentQuestion(
                question
        );

        return promptRenderer.render(promptTemplate);
    }
}