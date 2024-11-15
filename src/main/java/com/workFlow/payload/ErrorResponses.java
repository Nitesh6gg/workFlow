package com.workFlow.payload;

import java.time.LocalDateTime;

public record ErrorResponses(
        String message,
        int httpCode,
        String path,
        LocalDateTime timestamp,
        String traceId
) {
}
