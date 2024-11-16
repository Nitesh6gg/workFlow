package com.workflow.service;

import com.workflow.dto.request.SaveTaskDTO;
import com.workflow.dto.request.TaskAssignDto;
import com.workflow.entity.Task;
import com.workflow.exception.UnAuthoriseException;
import com.workflow.payload.MessageResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface TaskService {

    List<Map<String,Object>> getAllTeamLeaders(Principal principal);

    MessageResponse saveTask(SaveTaskDTO dto, Principal principal);

    Page<Map<String,Object>> fetchTasks(Principal principal, Pageable pageable);

    Page<Task> assignTask(String taskType, Principal principal, Pageable pageable);

    MessageResponse assignTaskToUser(TaskAssignDto dto, Principal principal) throws BadRequestException, UnAuthoriseException;
}
