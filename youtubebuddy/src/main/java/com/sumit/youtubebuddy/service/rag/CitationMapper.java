package com.sumit.youtubebuddy.service.rag;

import com.sumit.youtubebuddy.dto.citation.CitationDto;
import com.sumit.youtubebuddy.dto.chroma.ChromaResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CitationMapper {

    public List<CitationDto> map(List<ChromaResult> chunks) {

        List<CitationDto> citations = new ArrayList<>();

        if (chunks == null) {
            return citations;
        }

        for (int i = 0; i < chunks.size(); i++) {

            ChromaResult chunk = chunks.get(i);

            String preview = chunk.getDocument();

            if (preview.length() > 120) {
                preview = preview.substring(0, 120) + "...";
            }

            citations.add(
                    new CitationDto(
                            i + 1,
                            chunk.getDistance(),
                            preview
                    )
            );
        }

        return citations;



    }
}