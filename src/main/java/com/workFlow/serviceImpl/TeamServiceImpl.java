package com.workFlow.serviceImpl;

import com.workFlow.entity.Team;
import com.workFlow.helper.UserHelper;
import com.workFlow.payload.MessageResponse;
import com.workFlow.repository.TeamRepository;
import com.workFlow.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    UserHelper userHelper;

    @Autowired
    TeamRepository teamRepo;

    @Override
    public MessageResponse createTeam(Team team,Principal principal) {
        Team newTeam=new Team();
        newTeam.setTeamName(team.getTeamName());
        newTeam.setTeamLeader(team.getTeamLeader());
        newTeam.setCreatedBy(userHelper.getUserName(principal));
        newTeam.setCreationDate(String.valueOf(LocalDateTime.now()));
        teamRepo.save(newTeam);
        return new MessageResponse("Team created successfully", HttpStatus.OK);
    }
}
