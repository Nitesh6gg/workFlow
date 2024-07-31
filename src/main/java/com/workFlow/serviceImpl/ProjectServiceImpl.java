package com.workFlow.serviceImpl;

import com.workFlow.dto.request.ProjectDTO;
import com.workFlow.entity.Project;
import com.workFlow.entity.User;
import com.workFlow.helper.PaginationHelper;
import com.workFlow.helper.UserHelper;
import com.workFlow.payload.GlobalResponse;
import com.workFlow.payload.MessageResponse;
import com.workFlow.repository.ProjectRepository;
import com.workFlow.repository.UserRepository;
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
import java.util.*;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserHelper userHelper;
    @Autowired
    private UserRepository userRepository;


    @Override
    public MessageResponse createProject(ProjectDTO projectDTO, Principal principal) {
        try{
            int userType = userHelper.getUserType(principal);
            if(userType!=2)return new MessageResponse("Not authorized to perform this action", HttpStatus.UNAUTHORIZED);

            Optional<User> userId = userRepository.findByEmail(principal.getName());
            if(userId.isEmpty()) return new MessageResponse("Manager ID not found", HttpStatus.BAD_REQUEST);

            Project newproject=new Project();
            newproject.setManagerId(userId.get());
            newproject.setProjectName(projectDTO.projectName());
            newproject.setDescription(projectDTO.description());
            newproject.setStatus(projectDTO.status());
            newproject.setStartDate(projectDTO.startDate());
            newproject.setEndDate(projectDTO.endDate());
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

        Specification<Project> specs ;

        if (userType == 1) {
            specs= Specification.where(null);
        } else if (userType == 2) {
            specs = Specification.where(ProjectSpecifications.createdBy(userHelper.getUserName(principal)));
        } else return Page.empty();
        return projectRepo.findAll(specs,pageable);
    }

    @Override
    public List<Map<String,Object>> getAllProjectsForDropdown(Principal principal) {
        int userType = userHelper.getUserType(principal);
        return userType==2 ? projectRepo.findProjectsForDropdown(userHelper.getUserName(principal)):Collections.emptyList();
    }

    @Override
    public Page<?> getAllProjectsByStatus(String projectStatus, Pageable pageable, Principal principal) {
        int userType = userHelper.getUserType(principal);

        Specification<Project> specs = Specification.where(null);

        if(userType==1){
            specs = Specification.where(ProjectSpecifications.status(projectStatus));
        }else if (userType == 2) {
            specs = Specification.where(ProjectSpecifications.status(projectStatus)
                                 .and(ProjectSpecifications.createdBy(userHelper.getUserName(principal))));
        }else return Page.empty();
        return projectRepo.findAll(specs,pageable);
    }




}
