package com.sumit.youtubebuddy.dto.rag;

public class SemanticChunk {

    private String text;

    private long startMillis;

    private long endMillis;

    public SemanticChunk() {
    }

    public SemanticChunk(
            String text,
            long startMillis,
            long endMillis
    ) {
        this.text = text;
        this.startMillis = startMillis;
        this.endMillis = endMillis;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getStartMillis() {
        return startMillis;
    }

    public void setStartMillis(long startMillis) {
        this.startMillis = startMillis;
    }

    public long getEndMillis() {
        return endMillis;
    }

    public void setEndMillis(long endMillis) {
        this.endMillis = endMillis;
    }
}