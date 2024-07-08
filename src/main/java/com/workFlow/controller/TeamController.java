package com.workFlow.controller;

import com.workFlow.entity.Team;
import com.workFlow.payload.MessageResponse;
import com.workFlow.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createTeam(@RequestBody Team team, Principal principal){
        MessageResponse response = teamService.createTeam(team,principal);
        return new ResponseEntity<>(response, (HttpStatusCode) response.getHttpStatus());

    }


}
