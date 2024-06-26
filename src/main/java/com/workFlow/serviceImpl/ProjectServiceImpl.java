package com.workFlow.serviceImpl;

import com.workFlow.entity.Project;
import com.workFlow.helper.PaginationHelper;
import com.workFlow.helper.UserHelper;
import com.workFlow.payload.GlobalResponse;
import com.workFlow.payload.MessageResponse;
import com.workFlow.repository.ProjectRepository;
import com.workFlow.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<?> getAllProjects(int page, int size, String sortBy, String sortOrder, Principal principal) {
        Pageable pageable = PaginationHelper.createPageable(page, size, sortBy, sortOrder);

        int userType = userHelper.getUserType(principal);
        Page<List<Map<String, Object>>> projectPage;

        if (userType == 1) {
            projectPage = projectRepo.findAllForSuperAdmin(pageable);
        } else if (userType == 2) {
            projectPage = projectRepo.findAllForAdmin(userHelper.getUserName(principal),pageable);
        } else {
            return new ResponseEntity<>(new GlobalResponse("You are not authorized to perform this action", HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        }

        List<List<Map<String, Object>>> projects = projectPage.getContent();
        Map<String, Object> response = PaginationHelper.createResponse(projectPage, projects);
        return ResponseEntity.ok(response);
    }
}
