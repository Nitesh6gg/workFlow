package com.workFlow.repository;

import com.workFlow.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhone(String phone);

    @Query(value = "SELECT u.`userId`,u.`username`,u.`email`,u.`firstName`,u.`lastName`,u.`phone`,u.`enabled`,u.`createdBy`,u.`createdON`,r.`roleType`,d.`name` AS departmentName,p.`title`AS positionName FROM `user` u JOIN `userrole` ur ON u.`userId`=ur.`userId` JOIN `role` r ON ur.`roleId`=r.`roleId` JOIN `department` d ON ur.`departmentId`=d.`id` JOIN `position` p ON ur.`positionId`=p.`positionId` WHERE u.createdBy= :username", nativeQuery = true)
    Page<List<Map<String,Object>>> findAllForAdmin(@Param("username") String username, Pageable pageable);

    @Query(value = "SELECT u.`userId`,u.`username`,u.`email`,u.`firstName`,u.`lastName`,u.`phone`,u.`enabled`,u.`createdBy`,u.`createdON`,r.`roleType`,d.`name` AS departmentName,p.`title`AS positionName FROM `user` u JOIN `userrole` ur ON u.`userId`=ur.`userId` JOIN `role` r ON ur.`roleId`=r.`roleId` JOIN `department` d ON ur.`departmentId`=d.`id` JOIN `position` p ON ur.`positionId`=p.`positionId`", nativeQuery = true)
    Page<List<Map<String,Object>>> findAllForSuperAdmin(Pageable pageable);

    @Query(value = "SELECT r.roleId FROM `user` u JOIN userrole ur ON u.userId=ur.userId JOIN `role` r ON ur.roleId=r.roleId WHERE u.email=?",nativeQuery = true)
    public int findRoleByEmail(@Param("email") String email);

    @Query(value = "SELECT r.roleType FROM `user` u JOIN userrole ur ON u.userId=ur.userId JOIN `role` r ON ur.roleId=r.roleId WHERE u.email=?",nativeQuery = true)
    public String findRoleTypeByEmail(@Param("email") String email);

    @Query(value = "SELECT u.username FROM `user` u JOIN userrole ur ON u.userId=ur.userId JOIN `role` r ON ur.roleId=r.roleId WHERE u.email=?",nativeQuery = true)
    public String findUsernameByPrincipal(@Param("email") String email);

    @Query(value ="SELECT u.`userId`,u.`username`,p.`title`FROM `user` u JOIN userrole r ON u.`userId`=r.`userId` JOIN `department` d ON r.`departmentId`=d.`id` JOIN `position` p ON r.`positionId`=p.`positionId` WHERE d.`id`=? AND u.`createdBy`=?",nativeQuery = true)
    List<Map<String, Object>> getAllManager(@Param("departmentId") int departmentId,@Param("username") String username);

    @Query(value ="SELECT u.`userId`,u.`username`,p.`title`FROM `user` u JOIN userrole r ON u.`userId`=r.`userId` JOIN `department` d ON r.`departmentId`=d.`id` JOIN `position` p ON r.`positionId`=p.`positionId` WHERE d.`id`=?",nativeQuery = true)
    List<Map<String, Object>> getAllManager(@Param("departmentId") int departmentId);

    @Query(value ="SELECT u.`userId`,u.`username`,d.`name` AS departmentName,p.`title`AS positionName FROM `user` u JOIN `userrole` ur ON u.`userId`=ur.`userId` JOIN `role` r ON ur.`roleId`=r.`roleId` JOIN `department` d ON ur.`departmentId`=d.`id` JOIN `position` p ON ur.`positionId`=p.`positionId` WHERE u.createdBy=?",nativeQuery = true)
    List<Map<String,Object>> findAllUsersDropdown(String userName);

}
