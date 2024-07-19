package com.workFlow.serviceImpl;

import com.workFlow.helper.UserHelper;
import com.workFlow.repository.TaskRepository;
import com.workFlow.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl  implements TaskService {

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private UserHelper userHelper;

    @Override
    public List<Map<String, Object>> getAllTeamLeaders(Principal principal) {
        return taskRepo.getAllTeamLeaders(userHelper.getUserName(principal));
    }
}
