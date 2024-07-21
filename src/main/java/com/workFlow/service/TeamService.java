package com.workFlow.service;

import com.workFlow.dto.request.SaveTeamDTO;
import com.workFlow.payload.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.security.Principal;
import java.util.Map;

public interface TeamService {

    MessageResponse createTeam(SaveTeamDTO dto, Principal principal);

    Page<Map<String,Object>> getAllTeams(Principal principal, Pageable pageable);


}
