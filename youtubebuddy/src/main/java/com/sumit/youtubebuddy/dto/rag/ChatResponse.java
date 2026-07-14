package com.sumit.youtubebuddy.dto.rag;

import java.util.List;

public class ChatResponse {
    
    private String answer;
    private List<Citation> citations;
    private RetrievalResult retrievalResult;

    public ChatResponse() {
    }

    public ChatResponse(String answer, List<Citation> citations, RetrievalResult retrievalResult) {
        this.answer = answer;
        this.citations = citations;
        this.retrievalResult = retrievalResult;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public List<Citation> getCitations() {
        return citations;
    }

    public void setCitations(List<Citation> citations) {
        this.citations = citations;
    }

    public RetrievalResult getRetrievalResult() {
        return retrievalResult;
    }

    public void setRetrievalResult(RetrievalResult retrievalResult) {
        this.retrievalResult = retrievalResult;
    }
}
