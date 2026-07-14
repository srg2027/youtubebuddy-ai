package com.sumit.youtubebuddy.graph.state;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReflectionResult implements java.io.Serializable {
    private boolean passed;
    private String reason;
    private String suggestion;
    private boolean retryRequired;
    private double confidenceScore;
}
