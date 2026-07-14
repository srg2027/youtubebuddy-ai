package com.sumit.youtubebuddy.service.prompt;

import org.springframework.stereotype.Service;

@Service
public class ContextFormatter {

    public String format(String context) {

        if (context == null || context.isBlank()) {

            return """
                    ==================== RETRIEVED KNOWLEDGE ====================

                    No relevant context was retrieved.

                    =============================================================
                    """;
        }

        return """
                ==================== RETRIEVED KNOWLEDGE ====================

                %s

                =============================================================
                """.formatted(context);
    }
}