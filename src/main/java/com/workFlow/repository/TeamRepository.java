package com.workFlow.repository;

import com.workFlow.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;

public interface TeamRepository extends JpaRepository<Team,Integer> {


    @Query(value = "SELECT te.`teamId`, te.`teamName`, te.`description`, te.`createdBy`, te.`creationDate`, u.`username` AS tl FROM `team` te \n" +
            "JOIN `user` u ON te.`teamLeader` = u.`userId` WHERE te.`createdBy`=?",nativeQuery = true)
    Page<Map<String,Object>> getAllTeams(String userName, Pageable pageable);

}
