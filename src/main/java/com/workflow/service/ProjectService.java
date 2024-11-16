package com.workflow.service;

import com.workflow.dto.request.ProjectDTO;
import com.workflow.payload.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface ProjectService {

    MessageResponse createProject(ProjectDTO projectDTO, Principal principal);

    Page<?> getAllProjects(Pageable pageable, Principal principal);

    List<Map<String,Object>> getAllProjectsForDropdown(Principal principal);

    Page<?>getAllProjectsByStatus(String projectStatus, Pageable pageable, Principal principal);


}
