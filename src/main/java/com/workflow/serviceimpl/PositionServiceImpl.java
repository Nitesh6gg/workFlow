package com.workflow.serviceimpl;

import com.workflow.repository.PositionRepository;
import com.workflow.service.PositionService;
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
