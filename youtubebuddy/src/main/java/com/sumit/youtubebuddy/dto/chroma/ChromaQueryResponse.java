package com.sumit.youtubebuddy.dto.chroma;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChromaQueryResponse {

    private List<List<String>> documents;
    private List<List<Double>> distances;
    private List<List<String>> ids;
    private List<List<Map<String, Object>>> metadatas;

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
    public List<List<Map<String, Object>>> getMetadatas() {
        return metadatas;
    }
    public void setMetadatas(
            List<List<Map<String, Object>>> metadatas
    ) {
        this.metadatas = metadatas;
    }
}