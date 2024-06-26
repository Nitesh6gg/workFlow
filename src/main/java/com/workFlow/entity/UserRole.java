package com.workFlow.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.ToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private int roleId;

    @ManyToOne
    @JoinColumn(name = "positionId")
    private Position positionId;

    @ManyToOne
    @JoinColumn(name = "departmentId")
    private Department departmentId;

    private String createdBy;
    private String updatedBy;
    private String createdON;
    private String updatedON;

}
