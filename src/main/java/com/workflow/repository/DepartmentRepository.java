package com.workflow.repository;


import com.workflow.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {

    Optional<Department> findByName(String name);

    @Query( value ="SELECT `id`,`name`,`description` FROM department",nativeQuery = true)
    List<Map<String,Object>> getAllDepartment();
}
