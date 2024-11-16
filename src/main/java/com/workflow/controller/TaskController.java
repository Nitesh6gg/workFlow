package com.workflow.controller;

import com.workflow.dto.request.SaveTaskDTO;
import com.workflow.dto.request.TaskAssignDto;
import com.workflow.entity.Task;
import com.workflow.payload.MessageResponse;
import com.workflow.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("api/task")
@CrossOrigin("*")
public class TaskController {

    private final TaskService taskService;

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
    ResponseEntity<?> fetchTasks(@RequestParam String type, Principal principal, Pageable pageable){
        return ResponseEntity.ok(taskService.fetchTasks(principal,pageable));
    }

    @GetMapping("/assign")
    ResponseEntity<Page<Task>> assignTask(@RequestParam String taskType, Principal principal, Pageable pageable){
        return ResponseEntity.ok(taskService.assignTask(taskType, principal, pageable));
    }

    @PostMapping("/assign")
    ResponseEntity<MessageResponse> assignTaskToUser(@RequestBody TaskAssignDto dto, Principal principal){
        taskService.assignTaskToUser(dto, principal);
        return ResponseEntity.ok().build();
    }



}
