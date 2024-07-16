package com.workFlow.controller;

import com.workFlow.dto.request.CreateUserDTO;
import com.workFlow.payload.GlobalResponse;
import com.workFlow.payload.MessageResponse;
import com.workFlow.repository.UserRepository;
import com.workFlow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private UserService userService;

    @PostMapping("/createAdmin")
    public ResponseEntity<GlobalResponse> createAdmin (@RequestBody Map<String,Object> requestBody, Principal principal){
         GlobalResponse response=userService.createAdmin(requestBody, principal);
         return new ResponseEntity<>(response, (HttpStatusCode) response.getHttpStatus());
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createUser (@RequestBody CreateUserDTO requestDto, Principal principal){
        MessageResponse response=userService.createUser(requestDto, principal);
        return new ResponseEntity<>(response, (HttpStatusCode) response.getHttpStatus());
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllUsers (Pageable pageable,Principal principal){
        return ResponseEntity.ok(userService.getAllUsers(pageable,principal));
    }

    @GetMapping("/managerDropdown")
    public List<Map<String,Object>> getAllManagerByDepartmentId(@RequestParam int departmentId,Principal principal){
        return userService.getAllManager(departmentId,principal);
    }



}
