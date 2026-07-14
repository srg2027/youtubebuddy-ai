package com.sumit.youtubebuddy.dto;

public class AskRequest {

    private String question;

    private String model;

    public AskRequest() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}