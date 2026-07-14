package com.sumit.youtubebuddy.dto.rag;

public class DocumentMetadata {

    private SourceType sourceType;
    private String sourceName;
    
    // YouTube specific
    private String videoTitle;
    private String videoId;
    private String videoUrl;
    private long startMillis;
    private long endMillis;
    
    // File/Document specific
    private String fileName;
    private int pageNumber;
    private int chunkIndex;

    // Website specific
    private String pageTitle;
    private String url;

    public DocumentMetadata() {
    }

    public DocumentMetadata(
            SourceType sourceType, String sourceName, String videoTitle, String videoId, 
            String videoUrl, long startMillis, long endMillis, 
            String fileName, int pageNumber, int chunkIndex,
            String pageTitle, String url
    ) {
        this.sourceType = sourceType;
        this.sourceName = sourceName;
        this.videoTitle = videoTitle;
        this.videoId = videoId;
        this.videoUrl = videoUrl;
        this.startMillis = startMillis;
        this.endMillis = endMillis;
        this.fileName = fileName;
        this.pageNumber = pageNumber;
        this.chunkIndex = chunkIndex;
        this.pageTitle = pageTitle;
        this.url = url;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getChunkIndex() {
        return chunkIndex;
    }

    public void setChunkIndex(int chunkIndex) {
        this.chunkIndex = chunkIndex;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle(String pageTitle) {
        this.pageTitle = pageTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
