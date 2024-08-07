package com.workFlow.service;

import com.workFlow.dto.request.SaveTaskDTO;
import com.workFlow.entity.Task;
import com.workFlow.payload.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface TaskService {

    List<Map<String,Object>> getAllTeamLeaders(Principal principal);

    MessageResponse saveTask(SaveTaskDTO dto, Principal principal);

    Page<Map<String,Object>> fetchTasks(Principal principal, Pageable pageable);
}
