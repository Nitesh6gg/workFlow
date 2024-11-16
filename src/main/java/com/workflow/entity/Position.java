package com.workflow.entity;

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
@Table(name = "position")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int positionId;
    @Column(unique = true)
    private String title;
    private String Description;
    private String level;
    private String createdBy;
    private String updatedBy;
    private String createdON;
    private String updatedON;

}
