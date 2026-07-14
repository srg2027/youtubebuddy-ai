package com.sumit.youtubebuddy.service.prompt;

import org.springframework.stereotype.Service;

@Service
public class ResponseFormatterService {

    public String getInstructions() {

        return """
                ==================== RESPONSE FORMAT ====================

                Follow these formatting rules:

                • Use short paragraphs.

                • Use bullet points whenever listing information.

                • Use headings for long explanations.

                • Preserve code formatting if code is requested.

                • Do not repeat the user's question.

                • Keep answers concise unless the user asks for details.

                • Explain technical concepts in simple language.

                • If using additional AI knowledge (outside the retrieved context),
                  clearly separate it under the heading:

                  Additional Background

                =========================================================
                """;
    }
}