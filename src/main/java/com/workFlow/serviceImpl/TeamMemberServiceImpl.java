package com.workFlow.serviceImpl;

import com.workFlow.dto.request.AddMembersDTO;
import com.workFlow.entity.Team;
import com.workFlow.entity.TeamMember;
import com.workFlow.entity.User;
import com.workFlow.helper.UserHelper;
import com.workFlow.payload.MessageResponse;
import com.workFlow.repository.TeamMemberRepository;
import com.workFlow.repository.TeamRepository;
import com.workFlow.repository.UserRepository;
import com.workFlow.service.TeamMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
public class TeamMemberServiceImpl implements TeamMemberService {

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TeamRepository teamRepo;

    @Autowired
    private TeamMemberRepository teamMemberRepo;

    @Override
    public MessageResponse addMembersToTeam(AddMembersDTO dto, Principal principal) {
        try{
            Optional<Team> team = teamRepo.findById(dto.getTeamId());
            if (team.isEmpty()) return new MessageResponse("Team not found", HttpStatus.BAD_REQUEST);

            List<User> users = userRepo.findAllById(Arrays.asList(dto.getMemberIds()));
            if (users.size()!= dto.getMemberIds().length) return new MessageResponse("Some users not found", HttpStatus.BAD_REQUEST);

            List<TeamMember> members = users.stream().map(user -> {
                TeamMember member = new TeamMember();
                member.setTeam(team.get());
                member.setUserId(user);
                member.setActive(true);
                member.setJoinDate(String.valueOf(LocalDateTime.now()));
                //member.setRole("defaultRole"); // Set the role as needed
                return member;
            }).collect(Collectors.toList());

            teamMemberRepo.saveAll(members);
            return new MessageResponse("Members added to the team", HttpStatus.OK);

        }catch (Exception e){
            log.warn("Something went wrong", e);
            return new MessageResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Map<String, Object>> findAllMembersByTeamId(String teamId) {
        return teamRepo.findallMembersByTeamId(teamId);
    }
}
