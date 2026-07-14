package com.sumit.youtubebuddy.service.model;

public interface ChatModel {

    boolean supports(AIModel model);

    String generateResponse(
            String prompt,
            AIModel model
    );
}