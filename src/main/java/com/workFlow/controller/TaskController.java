package com.workFlow.controller;

import com.workFlow.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequestMapping("api/task")
@CrossOrigin("*")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping("/tl-drop")
    ResponseEntity<?> fetchAllTeamLeaders(Principal principal){
        return ResponseEntity.ok(taskService.getAllTeamLeaders(principal));
    }

}
