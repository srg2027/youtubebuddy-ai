package com.sumit.youtubebuddy.service.model;

import com.sumit.youtubebuddy.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class ModelRegistryTests extends BaseIntegrationTest {

    @Autowired
    private ModelRegistry modelRegistry;

    @Test
    void testGetDefaultModel() {
        AIModel defaultModel = modelRegistry.getDefaultModel();
        assertNotNull(defaultModel);
        assertEquals("llama-3.3-70b-versatile", defaultModel.getModelName());
    }

    @Test
    void testFindByModelName_ExistingModel() {
        AIModel model = modelRegistry.findByModelName("qwen3:8b");
        assertNotNull(model);
        assertEquals(AIProvider.OLLAMA, model.getProvider());
    }

    @Test
    void testFindByModelName_UnknownModel() {
        assertThrows(IllegalArgumentException.class, () -> {
            modelRegistry.findByModelName("fake-model");
        });
    }

    @Test
    void testProviderMapping() {
        assertEquals(AIProvider.GEMINI, modelRegistry.findByModelName("gemini-2.5-flash").getProvider());
        assertEquals(AIProvider.GROQ, modelRegistry.findByModelName("llama-3.3-70b-versatile").getProvider());
        assertEquals(AIProvider.OLLAMA, modelRegistry.findByModelName("llama3.3:latest").getProvider());
    }
}
