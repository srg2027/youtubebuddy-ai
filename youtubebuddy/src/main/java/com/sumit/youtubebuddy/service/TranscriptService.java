package com.sumit.youtubebuddy.service;

import com.sumit.youtubebuddy.dto.TranscriptResponse;
import com.sumit.youtubebuddy.dto.VideoRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TranscriptService {

    private final RestTemplate restTemplate;

    public TranscriptService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getTranscript(String youtubeUrl) {

        VideoRequest request = new VideoRequest();
        request.setYoutubeUrl(youtubeUrl);

        TranscriptResponse response =
                restTemplate.postForObject(
                        "http://localhost:5000/transcript",
                        request,
                        TranscriptResponse.class
                );

        return response.getTranscript();
    }
}