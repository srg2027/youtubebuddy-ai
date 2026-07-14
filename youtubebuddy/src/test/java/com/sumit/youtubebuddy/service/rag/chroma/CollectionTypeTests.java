package com.sumit.youtubebuddy.service.rag.chroma;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollectionTypeTests {

    @Test
    void testCollectionTypeMapping() {
        assertEquals("youtube_transcripts_v2", CollectionType.YOUTUBE.getCollectionName());
        assertEquals("pdf_documents", CollectionType.PDF.getCollectionName());
        assertEquals("web_pages", CollectionType.WEBSITE.getCollectionName());
        assertEquals("personal_notes", CollectionType.NOTES.getCollectionName());
    }
}
