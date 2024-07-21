package com.workFlow.dto.request;

import lombok.Getter;

@Getter
public class AddMembersDTO {
    private int teamId;
    private Integer[] memberIds;
}
