package com.sumit.youtubebuddy.service.model;

public class AIModel {

    private AIProvider provider;

    private String modelName;

    private String displayName;

    private boolean supportsVision;

    private boolean supportsStreaming;

    private boolean supportsToolCalling;

    private int contextWindow;

    public AIModel() {
    }

    public AIModel(
            AIProvider provider,
            String modelName,
            String displayName,
            boolean supportsVision,
            boolean supportsStreaming,
            boolean supportsToolCalling,
            int contextWindow
    ) {
        this.provider = provider;
        this.modelName = modelName;
        this.displayName = displayName;
        this.supportsVision = supportsVision;
        this.supportsStreaming = supportsStreaming;
        this.supportsToolCalling = supportsToolCalling;
        this.contextWindow = contextWindow;
    }

    public AIProvider getProvider() {
        return provider;
    }

    public void setProvider(AIProvider provider) {
        this.provider = provider;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isSupportsVision() {
        return supportsVision;
    }

    public void setSupportsVision(boolean supportsVision) {
        this.supportsVision = supportsVision;
    }

    public boolean isSupportsStreaming() {
        return supportsStreaming;
    }

    public void setSupportsStreaming(boolean supportsStreaming) {
        this.supportsStreaming = supportsStreaming;
    }

    public boolean isSupportsToolCalling() {
        return supportsToolCalling;
    }

    public void setSupportsToolCalling(boolean supportsToolCalling) {
        this.supportsToolCalling = supportsToolCalling;
    }

    public int getContextWindow() {
        return contextWindow;
    }

    public void setContextWindow(int contextWindow) {
        this.contextWindow = contextWindow;
    }
}