package com.workflow.service;

import com.workflow.dto.request.SaveTeamDTO;
import com.workflow.payload.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.security.Principal;
import java.util.Map;

public interface TeamService {

    MessageResponse createTeam(SaveTeamDTO dto, Principal principal);

    Page<Map<String,Object>> getAllTeams(Principal principal, Pageable pageable);

    Page<Map<String,Object>> fetchAllAssignTeam(Principal principal,Pageable pageable);
}
