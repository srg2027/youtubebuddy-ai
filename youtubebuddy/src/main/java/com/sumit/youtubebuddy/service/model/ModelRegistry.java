package com.sumit.youtubebuddy.service.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ModelRegistry {

    private final List<AIModel> models = new ArrayList<>();

    public ModelRegistry() {

        // ==========================
        // Gemini Models
        // ==========================

        models.add(
                new AIModel(
                        AIProvider.GEMINI,
                        "gemini-2.5-flash",
                        "Gemini 2.5 Flash",
                        false,
                        true,
                        true,
                        1_000_000
                )
        );

        models.add(
                new AIModel(
                        AIProvider.GEMINI,
                        "gemini-2.5-pro",
                        "Gemini 2.5 Pro",
                        false,
                        true,
                        true,
                        2_000_000
                )
        );

        // ==========================
        // Groq Models
        // ==========================

        models.add(
                new AIModel(
                        AIProvider.GROQ,
                        "llama-3.3-70b-versatile",
                        "Groq Llama 3.3 70B",
                        false,
                        true,
                        true,
                        131072
                )
        );

        models.add(
                new AIModel(
                        AIProvider.GROQ,
                        "kimi-k2-instruct",
                        "Groq Kimi K2",
                        false,
                        true,
                        true,
                        131072
                )
        );

        // ==========================
        // Ollama Models
        // ==========================

        models.add(
                new AIModel(
                        AIProvider.OLLAMA,
                        "llama3.3:latest",
                        "Llama 3.3",
                        false,
                        true,
                        false,
                        131072
                )
        );

        models.add(
                new AIModel(
                        AIProvider.OLLAMA,
                        "deepseek-r1:8b",
                        "DeepSeek R1 (8B)",
                        false,
                        true,
                        false,
                        32768
                )
        );

        models.add(
                new AIModel(
                        AIProvider.OLLAMA,
                        "qwen3:8b",
                        "Qwen3 (8B)",
                        false,
                        true,
                        false,
                        32768
                )
        );

        models.add(
                new AIModel(
                        AIProvider.OLLAMA,
                        "gemma3:12b",
                        "Gemma 3 (12B)",
                        false,
                        true,
                        false,
                        32768
                )
        );
    }

    public List<AIModel> getModels() {
        return Collections.unmodifiableList(models);
    }

    @org.springframework.beans.factory.annotation.Value("${ai.default.model}")
    private String defaultModelName;

    public AIModel getDefaultModel() {
        return findByModelName(defaultModelName);
    }

    public AIModel findByModelName(String modelName) {

        return models.stream()
                .filter(model ->
                        model.getModelName()
                                .equalsIgnoreCase(modelName))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Unknown model : " + modelName
                        ));
    }
}