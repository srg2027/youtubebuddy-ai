package com.sumit.youtubebuddy.dto.video;



public class VideoMetadata {

    private String videoId;

    private String videoTitle;

    private String videoUrl;

    private int chunkIndex;

    private long startMillis;

    private long endMillis;

    public VideoMetadata() {
    }

    public VideoMetadata(
            String videoId,
            String videoTitle,
            String videoUrl,
            int chunkIndex,
            long startMillis,
            long endMillis
    ) {
        this.videoId = videoId;
        this.videoTitle = videoTitle;
        this.videoUrl = videoUrl;
        this.chunkIndex = chunkIndex;
        this.startMillis = startMillis;
        this.endMillis = endMillis;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public int getChunkIndex() {
        return chunkIndex;
    }

    public void setChunkIndex(int chunkIndex) {
        this.chunkIndex = chunkIndex;
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