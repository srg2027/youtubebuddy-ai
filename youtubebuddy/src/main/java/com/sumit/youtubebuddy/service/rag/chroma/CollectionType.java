package com.sumit.youtubebuddy.service.rag.chroma;

/**
 * Defines the supported ChromaDB collection types.
 * Each enum constant maps to a specific ChromaDB collection name.
 */
public enum CollectionType {

    YOUTUBE("youtube_transcripts_v2"),
    PDF("pdf_documents"),
    WEBSITE("web_pages"),
    NOTES("personal_notes");

    private final String collectionName;

    CollectionType(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getCollectionName() {
        return collectionName;
    }
}
