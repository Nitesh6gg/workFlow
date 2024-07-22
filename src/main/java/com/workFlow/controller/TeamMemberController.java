package com.workFlow.controller;

import com.workFlow.service.TeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/teamMember")
@CrossOrigin("*")
public class TeamMemberController {

    @Autowired
    private TeamMemberService teamMemberService;

    @RequestMapping("/find")
    ResponseEntity<?> fetchAllMembersByTeamId(@RequestParam("teamId") String teamId){
        return ResponseEntity.ok(teamMemberService.findAllMembersByTeamId(teamId));
    }
}
