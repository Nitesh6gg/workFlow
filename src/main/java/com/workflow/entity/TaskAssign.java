package com.workflow.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Table
public class TaskAssign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String taskName;
    private String taskDescription;
    private String priority;
    private String taskStatus;
    private long assignedBy;
    private long assignedTo;
    private LocalDateTime createdON;
    private LocalDateTime updatedON;

}
