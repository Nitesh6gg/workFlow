package com.workFlow.controller;

import com.workFlow.entity.Project;
import com.workFlow.payload.MessageResponse;
import com.workFlow.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
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
    ResponseEntity<MessageResponse> createProject(@RequestBody Project project, Principal principal){
        MessageResponse response=projectService.createProject(project,principal);
        return new ResponseEntity<>(response, (HttpStatusCode) response.getHttpStatus());
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllProjects (@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int size,
                                       @RequestParam(defaultValue = "createdDate") String sortBy,
                                       @RequestParam(defaultValue = "Desc") String sortOrder, Principal principal){
        return projectService.getAllProjects(page, size, sortBy, sortOrder, principal);
    }

    @GetMapping("/status")
    public ResponseEntity<?> getAllProjectsByStatus (@RequestParam String projectStatus,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "5") int size,
                                             @RequestParam(defaultValue = "createdDate") String sortBy,
                                             @RequestParam(defaultValue = "Desc") String sortOrder, Principal principal){
        return projectService.getAllProjectsByStatus(projectStatus,page, size, sortBy, sortOrder, principal);
    }

}
