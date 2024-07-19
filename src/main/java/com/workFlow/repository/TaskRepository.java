package com.workFlow.repository;

import com.workFlow.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface TaskRepository extends JpaRepository<Task,Integer> {

    @Query(value ="SELECT u.`userId`,u.`username`,d.`name` AS departmentName,p.`title`AS positionName FROM `user` u JOIN `userrole` ur ON u.`userId`=ur.`userId` JOIN `role` r ON ur.`roleId`=r.`roleId` JOIN `department` d ON ur.`departmentId`=d.`id` JOIN `position` p ON ur.`positionId`=p.`positionId` WHERE u.createdBy=? AND p.title='team leader'",nativeQuery = true)
    List<Map<String, Object>> getAllTeamLeaders(String username);
}
