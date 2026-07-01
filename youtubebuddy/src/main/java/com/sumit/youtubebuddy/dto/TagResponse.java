package com.sumit.youtubebuddy.dto;

public class TagResponse {

    private String tags;

    public TagResponse(String tags) {
        this.tags = tags;
    }

    public String getTags() {
        return tags;
    }
}