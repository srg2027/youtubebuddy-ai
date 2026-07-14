package com.sumit.youtubebuddy.service.prompt;

import org.springframework.stereotype.Service;

@Service
public class LanguageInstructionService {

    public String buildLanguageInstruction(String question) {

        if (containsHindi(question)) {

            return """
                    Respond ONLY in Hindi.

                    Do NOT answer in English unless the user explicitly asks.
                    """;
        }

        return """
                Respond ONLY in English.

                Do NOT answer in Hindi unless the user explicitly asks.
                """;
    }

    private boolean containsHindi(String text) {

        for (char ch : text.toCharArray()) {

            if (ch >= 0x0900 && ch <= 0x097F) {
                return true;
            }
        }

        return false;
    }
}