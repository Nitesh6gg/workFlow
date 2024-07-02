package com.workFlow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectId;
    private String projectName;
    private String description;
    private String status;
    private String progressBar;
    private String startDate;
    private String endDate;
    private String createdBy;
    private String updatedBy;
    private String createdON;
    private String updatedON;
    @ManyToOne
    @JoinColumn(name = "managerId")
    private User managerId;
}
