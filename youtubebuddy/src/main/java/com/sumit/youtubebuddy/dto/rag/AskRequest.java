package com.sumit.youtubebuddy.dto.rag;

public class AskRequest {

    private String question;
    private String modelName;

    public AskRequest() {
    }

    public AskRequest(String question, String modelName) {
        this.question = question;
        this.modelName = modelName;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
