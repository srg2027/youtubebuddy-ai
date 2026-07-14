package com.sumit.youtubebuddy.dto.prompt;

public class PromptTemplate {

    private String systemPrompt;
    private String languageInstruction;
    private String hallucinationGuard;
    private String responseFormatting;
    private String conversationHistory;
    private String retrievedContext;
    private String currentQuestion;

    public String getSystemPrompt() {
        return systemPrompt;
    }

    public void setSystemPrompt(String systemPrompt) {
        this.systemPrompt = systemPrompt;
    }

    public String getLanguageInstruction() {
        return languageInstruction;
    }

    public void setLanguageInstruction(String languageInstruction) {
        this.languageInstruction = languageInstruction;
    }

    public String getHallucinationGuard() {
        return hallucinationGuard;
    }

    public void setHallucinationGuard(String hallucinationGuard) {
        this.hallucinationGuard = hallucinationGuard;
    }

    public String getResponseFormatting() {
        return responseFormatting;
    }

    public void setResponseFormatting(String responseFormatting) {
        this.responseFormatting = responseFormatting;
    }

    public String getConversationHistory() {
        return conversationHistory;
    }

    public void setConversationHistory(String conversationHistory) {
        this.conversationHistory = conversationHistory;
    }

    public String getRetrievedContext() {
        return retrievedContext;
    }

    public void setRetrievedContext(String retrievedContext) {
        this.retrievedContext = retrievedContext;
    }

    public String getCurrentQuestion() {
        return currentQuestion;
    }

    public void setCurrentQuestion(String currentQuestion) {
        this.currentQuestion = currentQuestion;
    }
}