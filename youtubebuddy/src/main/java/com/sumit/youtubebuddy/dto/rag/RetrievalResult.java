package com.sumit.youtubebuddy.dto.rag;

import com.sumit.youtubebuddy.dto.chroma.ChromaResult;

import java.util.List;

public class RetrievalResult implements java.io.Serializable {

    private String context;

    private List<ChromaResult> chunks;

    public RetrievalResult() {
    }

    public RetrievalResult(
            String context,
            List<ChromaResult> chunks
    ) {
        this.context = context;
        this.chunks = chunks;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public List<ChromaResult> getChunks() {
        return chunks;
    }

    public void setChunks(List<ChromaResult> chunks) {
        this.chunks = chunks;
    }
}
