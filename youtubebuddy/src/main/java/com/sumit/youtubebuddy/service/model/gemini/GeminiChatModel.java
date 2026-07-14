package com.sumit.youtubebuddy.service.model.gemini;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.sumit.youtubebuddy.service.model.AIModel;
import com.sumit.youtubebuddy.service.model.AIProvider;
import com.sumit.youtubebuddy.service.model.ChatModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiChatModel implements ChatModel {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Override
    public boolean supports(AIModel model) {

        return model.getProvider() == AIProvider.GEMINI;
    }

    @Override
    public String generateResponse(
            String prompt,
            AIModel model
    ) {

        Client client = Client.builder()
                .apiKey(apiKey)
                .build();

        GenerateContentResponse response =
                client.models.generateContent(
                        model.getModelName(),
                        prompt,
                        null
                );

        return response.text();
    }
}