package com.workFlow.controller;

import com.workFlow.dto.request.AddMembersDTO;
import com.workFlow.dto.request.SaveTeamDTO;
import com.workFlow.payload.MessageResponse;
import com.workFlow.service.TeamMemberService;
import com.workFlow.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private TeamMemberService teamMemberService;

    @PostMapping("/save")
    public ResponseEntity<MessageResponse> saveTeam(@RequestBody SaveTeamDTO dto, Principal principal){
        MessageResponse response = teamService.createTeam(dto,principal);
        return new ResponseEntity<>(response, (HttpStatusCode) response.getHttpStatus());
    }

    @GetMapping("/list")
    public ResponseEntity<?> fetchAllTeams(Principal principal, Pageable pageable){
        return ResponseEntity.ok(teamService.getAllTeams(principal,pageable));
    }

    @PostMapping("/addMembers")
    public ResponseEntity<MessageResponse> addMembers(@RequestBody AddMembersDTO dto, Principal principal){
        MessageResponse response = teamMemberService.addMembersToTeam(dto,principal);
        return new ResponseEntity<>(response, (HttpStatusCode) response.getHttpStatus());
    }



}
