package com.workFlow.serviceImpl;

import com.workFlow.entity.Project;
import com.workFlow.helper.PaginationHelper;
import com.workFlow.helper.UserHelper;
import com.workFlow.payload.GlobalResponse;
import com.workFlow.payload.MessageResponse;
import com.workFlow.repository.ProjectRepository;
import com.workFlow.service.ProjectService;
import com.workFlow.specification.ProjectSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserHelper userHelper;


    @Override
    public MessageResponse createProject(Project project, Principal principal) {
        try{
            int userType = userHelper.getUserType(principal);
            if(userType!=2){
                return new MessageResponse("Not authorized to perform this action", HttpStatus.UNAUTHORIZED);
            }
            Project newproject=new Project();
            newproject.setManagerId(project.getManagerId());
            newproject.setProjectName(project.getProjectName());
            newproject.setDescription(project.getDescription());
            newproject.setStatus(project.getStatus());
            newproject.setStartDate(project.getStartDate());
            newproject.setEndDate(project.getEndDate());
            newproject.setCreatedBy(userHelper.getUserName(principal));
            newproject.setCreatedON(String.valueOf(new Date()));
            projectRepo.save(newproject);
            return new MessageResponse("created successfully",HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new MessageResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);}

    }

    @Override
    public Page<?> getAllProjects(Pageable pageable, Principal principal) {

        int userType = userHelper.getUserType(principal);

        if (userType == 1) {
            return projectRepo.findAllForSuperAdmin(pageable);
        } else if (userType == 2) {
            return projectRepo.findAllForAdmin(userHelper.getUserName(principal),pageable);
        } else {
            return Page.empty();
        }
    }

    @Override
    public Page<?> getAllProjectsByStatus(String projectStatus, Pageable pageable, Principal principal) {
        return null;
    }



   /* @Override
    public Page<List<Map<String,Object>>> agetAllProjectsByStatus(String projectStatus,Pageable pageable,Principal principal) {

        int userType = userHelper.getUserType(principal);

        Specification<Project> specs = Specification.where(null); // Initial specification

        if (projectStatus.equalsIgnoreCase("all") || projectStatus.equalsIgnoreCase("")) {
            if (userType == 1) {
                specs = null; // Fetch all projects without any filters
            } else if (userType == 2) {
                specs = Specification.where(ProjectSpecifications.createdBy(userHelper.getUserName(principal)));
            } else {
                return null;
            }
        } else {
            specs = Specification.where(ProjectSpecifications.status(projectStatus));
            if (userType == 2) {
                specs = specs.and(ProjectSpecifications.createdBy(userHelper.getUserName(principal)));
            } else if (userType != 1) {
                return null;
            }
        }
        return projectRepo.findAllForSuperAdmin();
    }*/


}
