package com.sumit.youtubebuddy.dto.chroma;
import com.sumit.youtubebuddy.dto.rag.DocumentMetadata;

public class ChromaResult implements java.io.Serializable {

    private final String document;
    private final double distance;
    private final String id;
    private DocumentMetadata metadata;

    public ChromaResult(
            String document,
            double distance,
            String id,
            DocumentMetadata metadata
    ) {
        this.document = document;
        this.distance = distance;
        this.id = id;
        this.metadata = metadata;
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
    public DocumentMetadata getMetadata() {
        return metadata;
    }
    public void setMetadata(DocumentMetadata metadata) {
        this.metadata = metadata;
    }
}
