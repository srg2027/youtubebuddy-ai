package com.sumit.youtubebuddy.dto;
import com.sumit.youtubebuddy.dto.citation.CitationDto;
import java.util.List;

public class AskResponse {

    private String answer;
    private List<CitationDto> citations;

    public AskResponse() {

    }

    public AskResponse(String answer) {
        this.answer = answer;
    }
    public AskResponse(
            String answer,
            List<CitationDto> citations
    ) {
        this.answer = answer;
        this.citations = citations;
    }
    public List<CitationDto> getCitations() {
        return citations;
    }

    public void setCitations(List<CitationDto> citations) {
        this.citations = citations;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}