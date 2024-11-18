package com.workflow.serviceimpl;

import com.workflow.dto.request.SaveTaskDTO;
import com.workflow.dto.request.TaskAssignDto;
import com.workflow.entity.*;
import com.workflow.exception.UnAuthoriseException;
import com.workflow.helper.PositionType;
import com.workflow.helper.UserHelper;
import com.workflow.payload.MessageResponse;
import com.workflow.repository.ProjectRepository;
import com.workflow.repository.TaskRepository;
import com.workflow.repository.UserRepository;
import com.workflow.repository.UserRoleRepository;
import com.workflow.service.TaskService;
import com.workflow.specification.TaskSpecifications;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Slf4j
@AllArgsConstructor
@Service
public class TaskServiceImpl  implements TaskService {
    private final UserRoleRepository userRoleRepository;

    private final TaskRepository taskRepo;
    private final UserRepository userRepo;
    private final UserRoleRepository userRoleRepo;
    private final ProjectRepository projectRepo;
    private final UserHelper userHelper;

    @Override
    public List<Map<String, Object>> getAllTeamLeaders(Principal principal) {
        return taskRepo.getAllTeamLeaders(userHelper.getUserName(principal));
    }

    @Override
    public MessageResponse saveTask(SaveTaskDTO dto, Principal principal) {
        try {
            Optional<User> byId = userRepo.findById(dto.getAssignedUserId());
            if (byId.isEmpty()) return new MessageResponse("User not found", HttpStatus.BAD_REQUEST);

            Optional<Project> byId1 = projectRepo.findById(dto.getProjectId());
            if (byId1.isEmpty()) return new MessageResponse("Project not found", HttpStatus.BAD_REQUEST);

            Task newTask = new Task();
            newTask.setProjectId(byId1.get());
            newTask.setDescription(dto.getDescription());
            newTask.setStatus(dto.getStatus());
            newTask.setAssignUserId(byId.get());
            newTask.setPriority(dto.getPriority());
            newTask.setProgressBar("0%");
            newTask.setBarColor("red");
            newTask.setStartDate(dto.getStartDate());
            newTask.setDueDate(dto.getDueDate());
            newTask.setCreatedBy(userHelper.getUserName(principal));
            taskRepo.save(newTask);
            return new MessageResponse("Task created", HttpStatus.OK);

        } catch (Exception e) {
            log.warn("Something went wrong", e);
            return new MessageResponse("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Page<Map<String,Object>> fetchTasks(Principal principal, Pageable pageable) {
        return taskRepo.fetchAllTasks(userHelper.getUsername(principal.getName()), pageable);
    }

    @Override
    public Page<Map<String,Object>> getAssignTask(String priority, Principal principal, Pageable pageable) {
            int userId = userHelper.getUserId(principal);
            return taskRepo.findAllAssignTaskByPriority(userId,priority,pageable);
    }

}
