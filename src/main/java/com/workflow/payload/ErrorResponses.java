package com.workflow.payload;

import java.time.LocalDateTime;

public record ErrorResponses(
        String message,
        String error,
        String requestURL,
        String httpMethod,
        int httpCode,
        LocalDateTime timestamp,
        String traceId
) {
}
