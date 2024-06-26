package com.workFlow.controller;

import com.workFlow.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/position")
@CrossOrigin("*")
public class PositionController {

    @Autowired
    private PositionService positionService;

    @RequestMapping("/dropdown")
    public List<Map<String,Object>> getAllPosition(Principal principal){
        return positionService.getAllPosition(principal);
    }
}
