package com.sumit.youtubebuddy.dto.chroma;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChromaQueryResponse {

    private List<List<String>> documents;
    private List<List<Double>> distances;
    private List<List<String>> ids;

    public List<List<String>> getDocuments() {
        return documents;
    }

    public void setDocuments(List<List<String>> documents) {
        this.documents = documents;
    }

    public List<List<Double>> getDistances() {
        return distances;
    }

    public void setDistances(List<List<Double>> distances) {
        this.distances = distances;
    }

    public List<List<String>> getIds() {
        return ids;
    }

    public void setIds(List<List<String>> ids) {
        this.ids = ids;
    }
}