package com.workflow.service;

import com.workflow.dto.request.AddMembersDTO;
import com.workflow.payload.MessageResponse;
import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface TeamMemberService {

    MessageResponse addMembersToTeam(AddMembersDTO dto, Principal principal);

    List<Map<String,Object>> findAllMembersByTeamId(String teamId);
}
