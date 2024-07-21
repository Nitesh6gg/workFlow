package com.workFlow.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "teamId")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User userId;
    private String joinDate;
    private boolean active=true;
    private String role;
}
