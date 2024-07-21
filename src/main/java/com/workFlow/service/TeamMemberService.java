package com.workFlow.service;

import com.workFlow.dto.request.AddMembersDTO;
import com.workFlow.payload.MessageResponse;
import java.security.Principal;

public interface TeamMemberService {

    MessageResponse addMembersToTeam(AddMembersDTO dto, Principal principal);
}
