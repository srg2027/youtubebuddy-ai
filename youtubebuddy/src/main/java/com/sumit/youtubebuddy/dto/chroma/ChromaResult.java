package com.sumit.youtubebuddy.dto.chroma;

public class ChromaResult {

    private final String document;
    private final double distance;
    private final String id;

    public ChromaResult(
            String document,
            double distance,
            String id
    ) {
        this.document = document;
        this.distance = distance;
        this.id = id;
    }

    public String getDocument() {
        return document;
    }

    public double getDistance() {
        return distance;
    }

    public String getId() {
        return id;
    }
}