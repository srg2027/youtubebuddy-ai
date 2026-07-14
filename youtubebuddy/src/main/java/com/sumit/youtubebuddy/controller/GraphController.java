package com.sumit.youtubebuddy.controller;

import com.sumit.youtubebuddy.dto.graph.GraphExecutionResponse;
import com.sumit.youtubebuddy.dto.graph.GraphRequest;
import com.sumit.youtubebuddy.service.graph.GraphChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/graph")
public class GraphController {

    private final GraphChatService graphChatService;

    public GraphController(GraphChatService graphChatService) {
        this.graphChatService = graphChatService;
    }

    @PostMapping("/chat")
    public ResponseEntity<GraphExecutionResponse> chat(@RequestBody GraphRequest request) {
        GraphExecutionResponse response = graphChatService.executeChat(request);
        return ResponseEntity.ok(response);
    }
}
