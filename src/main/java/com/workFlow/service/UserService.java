package com.workFlow.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import com.workFlow.payload.GlobalResponse;
import org.springframework.http.ResponseEntity;

public interface UserService {

    GlobalResponse createAdmin(Map<String, Object> requestBody, Principal principal);

    GlobalResponse createUser(Map<String, Object> requestBody, Principal principal);

    ResponseEntity<?> getUsers(int page, int size, String sortBy, String sortOrder, Principal principal);

    List<Map<String, Object>> getAllManager(int departmentId,Principal principal);
}
