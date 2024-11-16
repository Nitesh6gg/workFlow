package com.workflow.repository;


import com.workflow.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

     Optional<Role> findByRoleType(String roleType);
}
