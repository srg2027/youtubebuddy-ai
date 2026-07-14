package com.sumit.youtubebuddy.service.model;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModelRouterService {

    private final List<ChatModel> chatModels;
    private final ModelRegistry modelRegistry;

    public ModelRouterService(
            List<ChatModel> chatModels,
            ModelRegistry modelRegistry
    ) {
        this.chatModels = chatModels;
        this.modelRegistry = modelRegistry;
    }

    public String generateResponse(
            String prompt,
            String modelName
    ) {

        if (modelName == null || modelName.isBlank()) {
            modelName = modelRegistry
                    .getDefaultModel()
                    .getModelName();
        }

        AIModel model =
                modelRegistry.findByModelName(modelName);

        ChatModel chatModel = null;

        for (ChatModel provider : chatModels) {
            if (provider.supports(model)) {
                chatModel = provider;
                break;
            }
        }

        if (chatModel == null) {
            throw new RuntimeException(
                    "No provider found for model: " + modelName
            );
        }

        return chatModel.generateResponse(
                prompt,
                model
        );
    }

    public String generateResponse(
            String prompt
    ) {
        return generateResponse(
                prompt,
                null
        );
    }
}