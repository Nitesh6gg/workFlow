package com.workFlow.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;

    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project projectId;

    @Column(nullable = false)
    private String description;

    private String status;
    private String priority;

    @ManyToOne
    @JoinColumn(name = "assignUserId")
    private User assignUserId;

    private String startDate;
    private String dueDate;
    private String createdBy;
}
