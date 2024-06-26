package com.workFlow.repository;


import com.workFlow.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position,Integer> {

    Optional<Position> findByTitle(String title);

    @Query(value = "SELECT `positionId`,`title`,`Description` FROM `position`",nativeQuery = true)
    List<Map<String,Object>> getAllPosition();
}
