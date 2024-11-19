package com.workflow.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "task")
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
    private String progressBar;
    private String barColor;

    @ManyToOne
    @JoinColumn(name = "assignUserId")
    private User assignUserId;

    private String startDate;
    private String dueDate;
    private LocalDateTime createdON;
    private LocalDateTime assignDate;
    private String createdBy;
}
