package com.workFlow.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name="team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int teamId;
    private String teamName;
    private String description;
    private String creationDate;
    @ManyToOne
    @JoinColumn(name = "teamLeader")
    private User teamLeader;

    private String createdBy;
    @OneToMany(mappedBy = "team", cascade =CascadeType.ALL)
    private Set<TeamMember> members = new HashSet<>();

    private String color;
}
