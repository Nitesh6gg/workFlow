package com.workFlow.dto.request;

public record ProjectDTO(
        String projectName,
        int managerId,
        String description,
        String status,
        String startDate,
        String endDate,
        String createdBy,
        String createdON ) {
}
