package com.sumit.youtubebuddy.controller;

import com.sumit.youtubebuddy.dto.rag.AskRequest;
import com.sumit.youtubebuddy.dto.rag.ChatResponse;
import com.sumit.youtubebuddy.dto.rag.IngestionResponse;
import com.sumit.youtubebuddy.service.PdfChatService;
import com.sumit.youtubebuddy.service.rag.pdf.PdfIngestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    private final PdfIngestionService pdfIngestionService;
    private final PdfChatService pdfChatService;

    public PdfController(
            PdfIngestionService pdfIngestionService,
            PdfChatService pdfChatService
    ) {
        this.pdfIngestionService = pdfIngestionService;
        this.pdfChatService = pdfChatService;
    }

    @PostMapping("/ingest")
    public ResponseEntity<String> ingestPdf(@org.springframework.web.bind.annotation.RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a valid PDF file");
        }
        
        try {
            String result = pdfIngestionService.ingestPdf(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error processing PDF: " + e.getMessage());
        }
    }

    @PostMapping("/ask")
    public ResponseEntity<ChatResponse> askPdf(@RequestBody AskRequest request) {
        if (request.getQuestion() == null || request.getQuestion().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            ChatResponse response = pdfChatService.askQuestion(
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
