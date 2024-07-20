package com.workFlow.dto.request;

import lombok.Data;
import lombok.Getter;

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
