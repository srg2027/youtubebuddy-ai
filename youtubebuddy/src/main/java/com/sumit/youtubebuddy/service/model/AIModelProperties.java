package com.sumit.youtubebuddy.service.model;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "youtubebuddy.ai")
public class AIModelProperties {

    private String defaultModel;

    private List<AIModel> models = new ArrayList<>();

    public String getDefaultModel() {
        return defaultModel;
    }

    public void setDefaultModel(String defaultModel) {
        this.defaultModel = defaultModel;
    }

    public List<AIModel> getModels() {
        return models;
    }

    public void setModels(List<AIModel> models) {
        this.models = models;
    }
}