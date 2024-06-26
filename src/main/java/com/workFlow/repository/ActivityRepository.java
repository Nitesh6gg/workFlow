package com.workFlow.repository;

import com.workFlow.entity.ActivityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<ActivityLog,Integer> {
}
