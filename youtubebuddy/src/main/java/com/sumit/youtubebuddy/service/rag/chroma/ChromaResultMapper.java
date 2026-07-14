package com.sumit.youtubebuddy.service.rag.chroma;

import com.sumit.youtubebuddy.dto.chroma.ChromaQueryResponse;
import com.sumit.youtubebuddy.dto.chroma.ChromaResult;
import com.sumit.youtubebuddy.dto.rag.DocumentMetadata;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ChromaResultMapper {

    private final ChromaMetadataMapper metadataMapper;

    public ChromaResultMapper(
            ChromaMetadataMapper metadataMapper
    ) {
        this.metadataMapper = metadataMapper;
    }

    public List<ChromaResult> map(
            ChromaQueryResponse response
    ) {

        List<ChromaResult> results =
                new ArrayList<>();

        List<String> documents =
                response.getDocuments().get(0);

        List<Double> distances =
                response.getDistances().get(0);

        List<String> ids =
                response.getIds().get(0);

        List<Map<String, Object>> metadatas =
                response.getMetadatas() != null
                        ? response.getMetadatas().get(0)
                        : Collections.emptyList();

        for (int i = 0; i < documents.size(); i++) {

            Map<String, Object> metadata =
                    metadatas.size() > i
                            ? metadatas.get(i)
                            : Collections.emptyMap();

            DocumentMetadata docMetadata =
                    metadataMapper.map(metadata);

            results.add(

                    new ChromaResult(

                            documents.get(i),

                            distances.get(i),

                            ids.get(i),

                            docMetadata
                    )
            );
        }

        return results;
    }
}