package com.sumit.youtubebuddy.dto.rag;

public class WebsiteIngestionRequest {
    
    private String url;

    public WebsiteIngestionRequest() {
    }

    public WebsiteIngestionRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
