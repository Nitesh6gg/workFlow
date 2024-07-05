package com.workFlow.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;
    @Column(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String phone;
    @JsonIgnore
    private String password;
    private Boolean enabled=true;
    private String createdBy;
    private String updatedBy;
    private String createdON;
    private String updatedON;

}
