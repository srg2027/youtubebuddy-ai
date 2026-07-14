package com.sumit.youtubebuddy.controller;

import com.sumit.youtubebuddy.service.model.ModelRouterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestModelController {

    private final ModelRouterService modelRouterService;

    public TestModelController(ModelRouterService modelRouterService) {
        this.modelRouterService = modelRouterService;
    }

    @GetMapping("/test/model")
    public String testModel(
            @RequestParam String prompt,
            @RequestParam(required = false) String modelName) {
        if (modelName != null) {
            return modelRouterService.generateResponse(prompt, modelName);
        } else {
            return modelRouterService.generateResponse(prompt);
        }
    }
}
