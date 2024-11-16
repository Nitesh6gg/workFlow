package com.workflow.dto.request;

import lombok.Data;

@Data
public class SaveTaskDTO {
    private int projectId;
    private String description;
    private String status;
    private String priority;
    private int assignedUserId;
    private String startDate;
    private String dueDate;
}
