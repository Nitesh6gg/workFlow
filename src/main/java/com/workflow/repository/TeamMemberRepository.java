package com.workflow.repository;

import com.workflow.entity.Team;
import com.workflow.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember,Integer> {

    List<TeamMember> findByTeamAndUserIdIn(Team team, List<Integer> userIds);

}
