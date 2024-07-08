package com.workFlow.service;

import com.workFlow.entity.Team;
import com.workFlow.payload.MessageResponse;

import java.security.Principal;

public interface TeamService {

    MessageResponse createTeam(Team team,Principal principal);

}
