package com.workFlow.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import com.workFlow.payload.GlobalResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface UserService {

    GlobalResponse createAdmin(Map<String, Object> requestBody, Principal principal);

    GlobalResponse createUser(Map<String, Object> requestBody, Principal principal);

    Page<?> getAllUsers(Pageable pageable,Principal principal);

    List<Map<String, Object>> getAllManager(int departmentId,Principal principal);
}
