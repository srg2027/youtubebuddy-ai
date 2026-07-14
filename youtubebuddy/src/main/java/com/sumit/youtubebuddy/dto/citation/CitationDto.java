package com.sumit.youtubebuddy.dto.citation;

public class CitationDto {

    private int chunkNumber;

    private double similarityScore;

    private String preview;

    public CitationDto() {
    }

    public CitationDto(
            int chunkNumber,
            double similarityScore,
            String preview
    ) {
        this.chunkNumber = chunkNumber;
        this.similarityScore = similarityScore;
        this.preview = preview;
    }

    public int getChunkNumber() {
        return chunkNumber;
    }

    public void setChunkNumber(int chunkNumber) {
        this.chunkNumber = chunkNumber;
    }

    public double getSimilarityScore() {
        return similarityScore;
    }

    public void setSimilarityScore(double similarityScore) {
        this.similarityScore = similarityScore;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }
}