package com.sumit.youtubebuddy.controller;

import com.sumit.youtubebuddy.dto.PromptRequest;
import com.sumit.youtubebuddy.dto.PromptResponse;
import com.sumit.youtubebuddy.service.GeminiService;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GeminiController {

    private final GeminiService geminiService;

    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/chat")
    public PromptResponse chat(
            @RequestBody PromptRequest request) {

        String response =
                geminiService.generateResponse(
                        request.getPrompt());

        return new PromptResponse(response);
    }
}