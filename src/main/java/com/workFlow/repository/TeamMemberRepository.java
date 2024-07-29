package com.workFlow.repository;

import com.workFlow.entity.Team;
import com.workFlow.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember,Integer> {

    List<TeamMember> findByTeamAndUserIdIn(Team team, List<Integer> userIds);

}
