package com.sumit.youtubebuddy.graph.state;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolResult implements java.io.Serializable {
    private String toolName;
    private String status;
    private Object payload;
    private Map<String, Object> metadata;
}
