package com.workflow.serviceImpl;

import com.workflow.dto.request.SaveTeamDTO;
import com.workflow.entity.Team;
import com.workflow.entity.TeamMember;
import com.workflow.entity.User;
import com.workflow.helper.ColorGenerator;
import com.workflow.helper.UserHelper;
import com.workflow.payload.MessageResponse;
import com.workflow.repository.TeamMemberRepository;
import com.workflow.repository.TeamRepository;
import com.workflow.repository.UserRepository;
import com.workflow.service.TeamMemberService;
import com.workflow.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    TeamMemberService teamMemberService;

    @Autowired
    TeamMemberRepository teamMemberRepo;

    @Autowired
    TeamRepository teamRepo;

    @Override
    @Transactional
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

            TeamMember saveMember =new TeamMember();
            saveMember.setUserId(leaderId.get());
            saveMember.setTeam(teamRepo.save(newTeam));
            saveMember.setActive(true);
            saveMember.setJoinDate(String.valueOf(LocalDateTime.now()));
            teamMemberRepo.save(saveMember);

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
