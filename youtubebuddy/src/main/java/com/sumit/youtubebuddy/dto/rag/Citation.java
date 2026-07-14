package com.sumit.youtubebuddy.dto.rag;

public class Citation implements java.io.Serializable {
    private SourceType sourceType;
    private String sourceName;
    private String title;
    private String url;
    private Integer chunkIndex;
    private String details;

    public Citation() {
    }

    public Citation(SourceType sourceType, String sourceName, String title, String url, Integer chunkIndex, String details) {
        this.sourceType = sourceType;
        this.sourceName = sourceName;
        this.title = title;
        this.url = url;
        this.chunkIndex = chunkIndex;
        this.details = details;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getChunkIndex() {
        return chunkIndex;
    }

    public void setChunkIndex(Integer chunkIndex) {
        this.chunkIndex = chunkIndex;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
