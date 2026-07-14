package com.sumit.youtubebuddy.dto.pdf;

public class PdfMetadata {
    private String fileName;
    private int chunkIndex;

    public PdfMetadata() {
    }

    public PdfMetadata(String fileName, int chunkIndex) {
        this.fileName = fileName;
        this.chunkIndex = chunkIndex;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getChunkIndex() {
        return chunkIndex;
    }

    public void setChunkIndex(int chunkIndex) {
        this.chunkIndex = chunkIndex;
    }
}
