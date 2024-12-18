package com.workflow.controller;

import com.workflow.dto.request.AddMembersDTO;
import com.workflow.dto.request.SaveTeamDTO;
import com.workflow.payload.MessageResponse;
import com.workflow.service.TeamMemberService;
import com.workflow.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.Map;

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
    public ResponseEntity<Page<Map<String,Object>>> fetchAllTeams(Principal principal, Pageable pageable){
        return ResponseEntity.ok(teamService.getAllTeams(principal,pageable));
    }

    @PostMapping("/addMembers")
    public ResponseEntity<MessageResponse> addMembers(@RequestBody AddMembersDTO dto, Principal principal){
        MessageResponse response = teamMemberService.addMembersToTeam(dto,principal);
        return new ResponseEntity<>(response, (HttpStatusCode) response.getHttpStatus());
    }

    @GetMapping("/assign")
    public ResponseEntity<Page<Map<String,Object>>> fetchAllAssignTeam(Principal principal,Pageable pageable){
        return ResponseEntity.ok(teamService.fetchAllAssignTeam(principal,pageable));
    }



}
