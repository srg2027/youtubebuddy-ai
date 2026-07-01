package com.sumit.youtubebuddy.dto;

public class VideoResponse {

    private String summary;

    public VideoResponse(String summary) {
        this.summary = summary;
    }

    public String getSummary() {
        return summary;
    }
}