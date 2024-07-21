package com.workFlow.repository;

import com.workFlow.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember,Integer> {
}
