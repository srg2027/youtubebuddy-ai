package com.sumit.youtubebuddy.service.model;

import com.sumit.youtubebuddy.BaseIntegrationTest;
import com.sumit.youtubebuddy.service.model.gemini.GeminiChatModel;
import com.sumit.youtubebuddy.service.model.groq.GroqChatModel;
import com.sumit.youtubebuddy.service.model.ollama.OllamaChatModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ModelRouterServiceTests extends BaseIntegrationTest {

    @Autowired
    private ModelRouterService modelRouterService;

    @MockitoBean
    private GroqChatModel groqChatModel;

    @MockitoBean
    private OllamaChatModel ollamaChatModel;

    @MockitoBean
    private GeminiChatModel geminiChatModel;

    @Test
    void testUnknownModelException() {
        assertThrows(IllegalArgumentException.class, () -> {
            modelRouterService.generateResponse("Hello", "fake-model-does-not-exist");
        });
    }

    @Test
    void testGroqRouting() {
        when(groqChatModel.supports(any())).thenReturn(true);
        when(groqChatModel.generateResponse(anyString(), any())).thenReturn("Groq Response");

        String response = modelRouterService.generateResponse("Hello", "llama-3.3-70b-versatile");
        assertEquals("Groq Response", response);
        verify(groqChatModel, times(1)).generateResponse(eq("Hello"), any());
    }

    @Test
    void testOllamaRouting() {
        when(ollamaChatModel.supports(any())).thenReturn(true);
        when(ollamaChatModel.generateResponse(anyString(), any())).thenReturn("Ollama Response");

        String response = modelRouterService.generateResponse("Hello", "qwen3:8b");
        assertEquals("Ollama Response", response);
        verify(ollamaChatModel, times(1)).generateResponse(eq("Hello"), any());
    }

    @Test
    void testGeminiRouting() {
        when(geminiChatModel.supports(any())).thenReturn(true);
        when(geminiChatModel.generateResponse(anyString(), any())).thenReturn("Gemini Response");

        String response = modelRouterService.generateResponse("Hello", "gemini-2.5-flash");
        assertEquals("Gemini Response", response);
        verify(geminiChatModel, times(1)).generateResponse(eq("Hello"), any());
    }

    @Test
    void testNullModelFallsBackToDefault() {
        when(groqChatModel.supports(any())).thenReturn(true);
        when(groqChatModel.generateResponse(anyString(), any())).thenReturn("Default Response");

        String response = modelRouterService.generateResponse("Hello", null);
        assertEquals("Default Response", response);
        verify(groqChatModel, times(1)).generateResponse(eq("Hello"), argThat(model -> 
            model.getModelName().equals("llama-3.3-70b-versatile")
        ));
    }
}

