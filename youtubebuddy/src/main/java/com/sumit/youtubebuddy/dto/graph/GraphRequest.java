package com.sumit.youtubebuddy.dto.graph;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GraphRequest {
    private String question;
    private String modelName;
}
