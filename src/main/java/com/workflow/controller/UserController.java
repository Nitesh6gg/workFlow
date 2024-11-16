package com.workflow.controller;

import com.workflow.dto.request.CreateUserDTO;
import com.workflow.payload.MessageResponse;
import com.workflow.repository.UserRepository;
import com.workflow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createUser (@RequestBody CreateUserDTO requestDto, Principal principal){
        MessageResponse response=userService.createUser(requestDto, principal);
        return new ResponseEntity<>(response, (HttpStatusCode) response.getHttpStatus());
    }

    @PostMapping("/upload-profileImg")
    public ResponseEntity<MessageResponse> uploadImage (@RequestParam("file") MultipartFile file, Principal principal){
        MessageResponse response=userService.uploadImage(file, principal);
        return new ResponseEntity<>(response, (HttpStatusCode) response.getHttpStatus());
    }

    @GetMapping("/list")
    public ResponseEntity<?> getAllUsers (Pageable pageable,Principal principal){
        return ResponseEntity.ok(userService.getAllUsers(pageable,principal));
    }

    @GetMapping("/profileDetails")
    public ResponseEntity<Map<String,Object>> getUserProfileDetails(Principal principal){
        return ResponseEntity.ok(userService.getUserProfileDetails(principal));
    }

    @GetMapping("/drop")
    public ResponseEntity<?> getAllUsersDropdown (Principal principal){
        return ResponseEntity.ok(userService.getAllUsersDropdown(principal));
    }

    @GetMapping("/managerDropdown")
    public List<Map<String,Object>> getAllManagerByDepartmentId(@RequestParam int departmentId,Principal principal){
        return userService.getAllManager(departmentId,principal);
    }

}
