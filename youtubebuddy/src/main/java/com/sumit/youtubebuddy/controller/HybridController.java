package com.sumit.youtubebuddy.controller;

import com.sumit.youtubebuddy.dto.rag.AskRequest;
import com.sumit.youtubebuddy.dto.rag.ChatResponse;
import com.sumit.youtubebuddy.service.rag.hybrid.HybridChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hybrid")
public class HybridController {

    private final HybridChatService hybridChatService;

    public HybridController(HybridChatService hybridChatService) {
        this.hybridChatService = hybridChatService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> askHybridQuestion(@RequestBody AskRequest request) {
        if (request.getQuestion() == null || request.getQuestion().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            ChatResponse response = hybridChatService.askQuestion(
                    request.getQuestion(),
                    request.getModelName()
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
