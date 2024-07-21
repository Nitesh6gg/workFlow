package com.workFlow.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import com.workFlow.dto.request.CreateUserDTO;
import com.workFlow.payload.GlobalResponse;
import com.workFlow.payload.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserService {

    GlobalResponse createAdmin(Map<String, Object> requestBody, Principal principal);

    MessageResponse createUser(CreateUserDTO requestDto, Principal principal);

    Page<?> getAllUsers(Pageable pageable,Principal principal);

    List<Map<String, Object>> getAllManager(int departmentId,Principal principal);

    List<Map<String,Object>> getAllUsersDropdown(Principal principal);
}
