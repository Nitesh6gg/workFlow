package com.workFlow.serviceImpl;

import com.workFlow.dto.request.SaveTaskDTO;
import com.workFlow.entity.Project;
import com.workFlow.entity.Task;
import com.workFlow.entity.User;
import com.workFlow.helper.UserHelper;
import com.workFlow.payload.MessageResponse;
import com.workFlow.repository.ProjectRepository;
import com.workFlow.repository.TaskRepository;
import com.workFlow.repository.UserRepository;
import com.workFlow.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
@Slf4j
@Service
public class TaskServiceImpl  implements TaskService {

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private UserHelper userHelper;

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


}
