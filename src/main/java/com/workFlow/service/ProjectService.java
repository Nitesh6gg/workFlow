package com.workFlow.service;

import com.workFlow.entity.Project;
import com.workFlow.payload.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface ProjectService {

    MessageResponse createProject(Project project, Principal principal);

    Page<?> getAllProjects(Pageable pageable, Principal principal);

    Page<?>getAllProjectsByStatus(String projectStatus, Pageable pageable, Principal principal);

}
