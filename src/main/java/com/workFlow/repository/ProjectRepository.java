package com.workFlow.repository;

import com.workFlow.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ProjectRepository  extends JpaRepository<Project,Integer>, JpaSpecificationExecutor<Project> {

    @Query(value = "SELECT p.*,u.`username` AS managerName,u.`email`AS managerEmail FROM project p JOIN `user` u ON p.`managerId`=u.`userId` WHERE p.`createdBy`=:username", nativeQuery = true)
    Page<List<Map<String,Object>>> findAllForAdmin(@Param("username") String username, Pageable pageable);

    @Query(value = "SELECT p.*,u.`username` AS managerName,u.`email`AS managerEmail FROM project p JOIN `user` u ON p.`managerId`=u.`userId`", nativeQuery = true)
    Page<List<Map<String,Object>>> findAllForSuperAdmin(Pageable pageable);

    @Query(value = "SELECT projectId,projectName FROM project WHERE createdBy = ?",nativeQuery = true)
    List<Map<String, Object>> findProjectsForDropdown(String createdBy);

}
