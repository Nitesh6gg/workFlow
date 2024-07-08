package com.workFlow.dto.request;


import lombok.Data;

@Data
public class CreateUserDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int role;
    private int department;
    private int position;
    private String password;
}
