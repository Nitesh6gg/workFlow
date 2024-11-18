package com.workflow.repository;

import com.workflow.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface TaskRepository extends JpaRepository<Task,Integer>, JpaSpecificationExecutor<Task> {

    @Query(value ="SELECT u.`userId`,u.`username`,d.`name` AS departmentName,p.`title`AS positionName FROM `user` u JOIN `userrole` ur ON u.`userId`=ur.`userId` JOIN `role` r ON ur.`roleId`=r.`roleId` JOIN `department` d ON ur.`departmentId`=d.`id` JOIN `position` p ON ur.`positionId`=p.`positionId` WHERE u.createdBy=? AND p.title='team leader'",nativeQuery = true)
    List<Map<String, Object>> getAllTeamLeaders(String username);

    @Query(value = "SELECT t.`taskId`,t.`description`,t.`startDate`,t.`dueDate`,t.`priority`,t.`status`,t.`progressBar`,t.`barColor`,t.`createdBy`,p.`projectName`,u.`username` FROM `task` t JOIN `project` p ON t.`projectId`=p.`projectId` JOIN `user` u ON u.`userId`=t.`assignUserId` WHERE t.`createdBy`=?",nativeQuery = true)
    Page<Map<String,Object>> fetchAllTasks(String username, Pageable pageable);

    @Query(value = "SELECT * FROM task WHERE assignUserId = :userId AND (:priority IS NULL OR :priority = '' OR priority = :priority)", nativeQuery = true)
    Page<Map<String, Object>> findAllAssignTaskByPriority(@Param("userId") long userId, @Param("priority") String priority, Pageable pageable);

}
