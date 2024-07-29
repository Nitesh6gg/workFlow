package com.workFlow.serviceImpl;

import com.workFlow.dto.request.SaveTeamDTO;
import com.workFlow.entity.Team;
import com.workFlow.entity.User;
import com.workFlow.helper.ColorGenerator;
import com.workFlow.helper.UserHelper;
import com.workFlow.payload.MessageResponse;
import com.workFlow.repository.TeamRepository;
import com.workFlow.repository.UserRepository;
import com.workFlow.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class TeamServiceImpl implements TeamService {

    @Autowired
    UserHelper userHelper;

    @Autowired
    UserRepository userRepo;

    @Autowired
    TeamRepository teamRepo;

    @Override
    public MessageResponse createTeam(SaveTeamDTO dto, Principal principal) {
        try{
            Optional<User> leaderId = userRepo.findById(dto.getTeamLeader());
            if (leaderId.isEmpty()) return new MessageResponse("User not found", HttpStatus.BAD_REQUEST);

            Team newTeam=new Team();
            newTeam.setTeamName(dto.getName());
            newTeam.setTeamLeader(leaderId.get());
            newTeam.setDescription(dto.getDescription());
            newTeam.setCreatedBy(userHelper.getUserName(principal));
            newTeam.setCreationDate(String.valueOf(LocalDateTime.now()));
            newTeam.setColor(ColorGenerator.generateColor());
            teamRepo.save(newTeam);
            return new MessageResponse("Team created", HttpStatus.OK);

        }catch (Exception e){
            log.warn("Something went wrong", e);
            return new MessageResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Page<Map<String, Object>> getAllTeams(Principal principal, Pageable pageable) {
        return teamRepo.getAllTeams(userHelper.getUserName(principal),pageable);
    }

    @Override
    public Page<Map<String, Object>> fetchAllAssignTeam(Principal principal, Pageable pageable) {
        int userId = userHelper.getUserId(principal);
        return teamRepo.fetchAllAssignTeamByUserId(userId, pageable);
    }


}
