package com.sumit.youtubebuddy.service.rag;

import com.sumit.youtubebuddy.dto.rag.SemanticChunk;
import com.sumit.youtubebuddy.dto.transcript.TranscriptSegment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SemanticChunkingService {

    private static final int MAX_CHUNK_SIZE = 1000;

    public List<SemanticChunk> chunk(
            List<TranscriptSegment> segments
    ) {

        List<SemanticChunk> chunks =
                new ArrayList<>();

        if (segments == null || segments.isEmpty()) {
            return chunks;
        }

        StringBuilder builder =
                new StringBuilder();

        long chunkStart =
                segments.get(0).getStartMillis();

        long chunkEnd =
                chunkStart;

        for (TranscriptSegment segment : segments) {

            if (builder.length() > 0) {
                builder.append(" ");
            }

            builder.append(segment.getText());

            chunkEnd =
                    segment.getEndMillis();

            if (builder.length() >= MAX_CHUNK_SIZE) {

                chunks.add(
                        new SemanticChunk(
                                builder.toString().trim(),
                                chunkStart,
                                chunkEnd
                        )
                );

                builder = new StringBuilder();

                chunkStart =
                        chunkEnd;
            }
        }

        if (!builder.isEmpty()) {

            chunks.add(
                    new SemanticChunk(
                            builder.toString().trim(),
                            chunkStart,
                            chunkEnd
                    )
            );
        }

        return chunks;
    }
}