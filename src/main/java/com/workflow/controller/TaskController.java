package com.workflow.controller;

import com.workflow.dto.request.SaveTaskDTO;
import com.workflow.payload.MessageResponse;
import com.workflow.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/save")
    ResponseEntity<MessageResponse> saveTask(Principal principal, @RequestBody SaveTaskDTO dto){
        MessageResponse response=taskService.saveTask(dto, principal);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    ResponseEntity<?> fetchTasks(Principal principal, Pageable pageable){
        return ResponseEntity.ok(taskService.fetchTasks(principal,pageable));
    }

}
