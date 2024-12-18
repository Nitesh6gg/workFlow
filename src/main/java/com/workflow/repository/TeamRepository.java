package com.workflow.repository;

import com.workflow.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface TeamRepository extends JpaRepository<Team,Integer> {


    @Query(value = "SELECT te.`teamId`, te.`teamName`, te.`description`, te.`createdBy`, te.`creationDate`, u.`username` AS tl FROM `team` te \n" +
            "JOIN `user` u ON te.`teamLeader` = u.`userId` WHERE te.`createdBy`=?",nativeQuery = true)
    Page<Map<String,Object>> getAllTeams(String userName, Pageable pageable);

    @Query(value = "SELECT m.`id`,u.`username`,m.`active`,m.`joinDate`,m.`role` FROM `teammember` m JOIN `user` u ON m.`userId`=u.`userId` WHERE m.`teamId`=?",nativeQuery = true)
    List<Map<String,Object>> findallMembersByTeamId(String teamId);

    @Query(value = "SELECT t.teamId, t.teamName, u.username AS teamLeader, t.description, t.createdBy,t.color, tm.joinDate, tm.active FROM team t " +
            "JOIN teammember tm ON t.teamId = tm.teamId JOIN user u ON t.teamLeader = u.userId " +
            "WHERE tm.userId = ?", nativeQuery = true)
    Page<Map<String, Object>> fetchAllAssignTeamByUserId(int userId,Pageable pageable);

}
