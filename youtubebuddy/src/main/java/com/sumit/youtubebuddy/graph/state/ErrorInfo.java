package com.sumit.youtubebuddy.graph.state;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorInfo implements java.io.Serializable {
    private String nodeName;
    private String errorMessage;
    private String stackTraceSummary;
    private boolean recoverable;
    private Long timestamp;
}
