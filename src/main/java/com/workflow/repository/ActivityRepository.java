package com.workflow.repository;

import com.workflow.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<ActivityLog,Integer> {
}
