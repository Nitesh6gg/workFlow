package com.workFlow.controller;

import com.workFlow.dto.request.SaveTeamDTO;
import com.workFlow.entity.Team;
import com.workFlow.payload.MessageResponse;
import com.workFlow.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("api/team")
@CrossOrigin("*")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/save")
    public ResponseEntity<MessageResponse> saveTeam(@RequestBody SaveTeamDTO dto, Principal principal){
        MessageResponse response = teamService.createTeam(dto,principal);
        return new ResponseEntity<>(response, (HttpStatusCode) response.getHttpStatus());
    }


}
