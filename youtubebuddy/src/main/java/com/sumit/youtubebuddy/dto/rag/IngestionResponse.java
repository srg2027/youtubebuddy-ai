package com.sumit.youtubebuddy.dto.rag;

public class IngestionResponse {
    
    private String message;
    private String pageTitle;
    private int chunksStored;
    private String collection;

    public IngestionResponse() {
    }

    public IngestionResponse(String message, String pageTitle, int chunksStored, String collection) {
        this.message = message;
        this.pageTitle = pageTitle;
        this.chunksStored = chunksStored;
        this.collection = collection;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public int getChunksStored() {
        return chunksStored;
    }

    public void setChunksStored(int chunksStored) {
        this.chunksStored = chunksStored;
    }

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }
}
