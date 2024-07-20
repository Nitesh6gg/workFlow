package com.workFlow.service;

import com.workFlow.dto.request.SaveTeamDTO;
import com.workFlow.entity.Team;
import com.workFlow.payload.MessageResponse;

import java.security.Principal;

public interface TeamService {

    MessageResponse createTeam(SaveTeamDTO dto, Principal principal);

}
