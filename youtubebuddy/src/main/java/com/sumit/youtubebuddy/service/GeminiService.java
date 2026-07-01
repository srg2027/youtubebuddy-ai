package com.sumit.youtubebuddy.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String generateResponse(String prompt) {

        Client client = Client.builder()
                .apiKey(apiKey)
                .build();

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.0-flash",
                        prompt,
                        null
                );

        return response.text();
    }
    public String summarizeVideo(String transcript) {

        String prompt = """
        Summarize this YouTube video transcript.
        

        Transcript:
        %s
        """.formatted(transcript);

        return generateResponse(prompt);
    }
    public String generateKeyPoints(String transcript) {

        String prompt = """
            Extract the 5 most important key points
            from the following transcript.

            Transcript:
            """ + transcript;

        return generateResponse(prompt);
    }
    public String generateTitles(String transcript) {

        String prompt = """
        Generate 10 catchy, SEO-friendly YouTube titles
        based on the following transcript.

        Make titles engaging and clickable.

        Transcript:
        %s
        """.formatted(transcript);

        return generateResponse(prompt);
    }
    public String generateDescription(String transcript) {

        String prompt = """
        Generate a professional YouTube video description.

        Include:
        - Brief overview
        - Key topics covered
        - Call to action

        Transcript:
        """ + transcript;

        return generateResponse(prompt);
    }
    public String generateTags(String transcript) {

        String prompt = """
        Generate 20 SEO friendly YouTube tags.

        Return only tags.

        Transcript:
        """ + transcript;

        return generateResponse(prompt);
    }
    public String generateQuiz(String transcript) {

        String prompt = """
    Generate 10 multiple choice questions (MCQs)
    from the following transcript.

    Format:

    Q1.
    A)
    B)
    C)
    D)

    Transcript:
    """ + transcript;

        return generateResponse(prompt);
    }
}