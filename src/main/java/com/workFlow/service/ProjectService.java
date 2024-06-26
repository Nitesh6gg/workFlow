package com.workFlow.service;

import com.workFlow.entity.Project;
import com.workFlow.payload.MessageResponse;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

public interface ProjectService {

    MessageResponse createProject(Project project, Principal principal);

    ResponseEntity<?> getAllProjects(int page, int size, String sortBy, String sortOrder, Principal principal);
}
