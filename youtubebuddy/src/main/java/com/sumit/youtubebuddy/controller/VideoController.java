package com.sumit.youtubebuddy.controller;


import com.sumit.youtubebuddy.dto.VideoRequest;
import com.sumit.youtubebuddy.dto.VideoResponse;
import com.sumit.youtubebuddy.dto.transcript.TranscriptResponse;
import com.sumit.youtubebuddy.service.GeminiService;
import com.sumit.youtubebuddy.service.TranscriptService;
import com.sumit.youtubebuddy.dto.KeyPointsResponse;
import com.sumit.youtubebuddy.dto.TitleResponse;
import com.sumit.youtubebuddy.dto.DescriptionResponse;
import com.sumit.youtubebuddy.dto.TagResponse;
import com.sumit.youtubebuddy.dto.QuizResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/video")
public class VideoController {

    private final TranscriptService transcriptService;
    private final GeminiService geminiService;

    public VideoController(
            TranscriptService transcriptService,
            GeminiService geminiService) {

        this.transcriptService = transcriptService;
        this.geminiService = geminiService;
    }

    @PostMapping("/summary")
    public VideoResponse summarize(
            @RequestBody VideoRequest request) {

        TranscriptResponse transcriptResponse =
                transcriptService.getTranscript(
                        request.getYoutubeUrl()
                );

        String transcript =
                transcriptResponse.getTranscript();

        String summary =
                geminiService.summarizeVideo(
                        transcript);

        return new VideoResponse(summary);
    }
    @PostMapping("/keypoints")
    public KeyPointsResponse getKeyPoints(
            @RequestBody VideoRequest request) {

        TranscriptResponse transcriptResponse =
                transcriptService.getTranscript(
                        request.getYoutubeUrl()
                );

        String transcript =
                transcriptResponse.getTranscript();

        String keyPoints =
                geminiService.generateKeyPoints(
                        transcript);

        return new KeyPointsResponse(keyPoints);
    }

    @PostMapping("/titles")
    public TitleResponse generateTitles(
            @RequestBody VideoRequest request) {

        TranscriptResponse transcriptResponse =
                transcriptService.getTranscript(
                        request.getYoutubeUrl()
                );

        String transcript =
                transcriptResponse.getTranscript();

        String titles =
                geminiService.generateTitles(
                        transcript);

        return new TitleResponse(titles);
    }
    @PostMapping("/description")
    public DescriptionResponse generateDescription(
            @RequestBody VideoRequest request) {

        TranscriptResponse transcriptResponse =
                transcriptService.getTranscript(
                        request.getYoutubeUrl()
                );

        String transcript =
                transcriptResponse.getTranscript();

        String description =
                geminiService.generateDescription(
                        transcript);

        return new DescriptionResponse(description);
    }
    @PostMapping("/tags")
    public TagResponse generateTags(
            @RequestBody VideoRequest request) {

        TranscriptResponse transcriptResponse =
                transcriptService.getTranscript(
                        request.getYoutubeUrl()
                );

        String transcript =
                transcriptResponse.getTranscript();

        String tags =
                geminiService.generateTags(
                        transcript);

        return new TagResponse(tags);
    }
    @PostMapping("/quiz")
    public QuizResponse generateQuiz(
            @RequestBody VideoRequest request) {

        TranscriptResponse transcriptResponse =
                transcriptService.getTranscript(
                        request.getYoutubeUrl()
                );

        String transcript =
                transcriptResponse.getTranscript();

        String quiz =
                geminiService.generateQuiz(
                        transcript);

        return new QuizResponse(quiz);
    }
}
