package com.workFlow.controller;

import com.workFlow.entity.Project;
import com.workFlow.payload.MessageResponse;
import com.workFlow.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequestMapping("/api/project")
@CrossOrigin("*")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/create")
    ResponseEntity<MessageResponse> saveProject(@RequestBody Project project, Principal principal){
        MessageResponse response=projectService.createProject(project,principal);
        return new ResponseEntity<>(response, (HttpStatusCode) response.getHttpStatus());
    }
    @GetMapping("/list")
    public ResponseEntity<?> fetchAllProjects (Pageable pageable,Principal principal){
        return ResponseEntity.ok(projectService.getAllProjects(pageable,principal));
    }

    @GetMapping("/list-drop")
    public ResponseEntity<?> fetchAllProjectsForDropDown (Principal principal){
        return ResponseEntity.ok(projectService.getAllProjectsForDropdown(principal));
    }



    @GetMapping("/status")
    public ResponseEntity<?> getAllProjectsByStatus (@RequestParam String projectStatus,Pageable pageable,Principal principal){
        return ResponseEntity.ok(projectService.getAllProjectsByStatus(projectStatus,pageable, principal));
    }

}
