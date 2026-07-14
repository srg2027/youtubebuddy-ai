package com.sumit.youtubebuddy.controller;

import com.sumit.youtubebuddy.dto.rag.AskRequest;
import com.sumit.youtubebuddy.dto.rag.ChatResponse;
import com.sumit.youtubebuddy.dto.rag.IngestionResponse;
import com.sumit.youtubebuddy.dto.rag.WebsiteIngestionRequest;
import com.sumit.youtubebuddy.service.rag.website.WebsiteChatService;
import com.sumit.youtubebuddy.service.rag.website.WebsiteIngestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/website")
public class WebsiteController {

    private final WebsiteIngestionService websiteIngestionService;
    private final WebsiteChatService websiteChatService;

    public WebsiteController(
            WebsiteIngestionService websiteIngestionService,
            WebsiteChatService websiteChatService
    ) {
        this.websiteIngestionService = websiteIngestionService;
        this.websiteChatService = websiteChatService;
    }

    @PostMapping("/ingest")
    public ResponseEntity<IngestionResponse> ingestWebsite(@RequestBody WebsiteIngestionRequest request) {
        if (request.getUrl() == null || request.getUrl().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            IngestionResponse response = websiteIngestionService.ingestWebsite(request.getUrl());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/ask")
    public ResponseEntity<ChatResponse> askWebsite(@RequestBody AskRequest request) {
        if (request.getQuestion() == null || request.getQuestion().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            ChatResponse response = websiteChatService.askQuestion(
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
