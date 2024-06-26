package com.workFlow.serviceImpl;

import com.workFlow.repository.PositionRepository;
import com.workFlow.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Map;
@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionRepository positionRepo;
    @Override
    public List<Map<String, Object>> getAllPosition(Principal principal) {
        return positionRepo.getAllPosition();
    }
}
