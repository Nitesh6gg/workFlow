package com.workflow.dto.request;

import java.time.LocalDateTime;
import java.util.List;

public record TaskAssignDto(
        long assignBy,
        List<Long> assignTo,
        String description,
        String priority,
        String status
) {
}
