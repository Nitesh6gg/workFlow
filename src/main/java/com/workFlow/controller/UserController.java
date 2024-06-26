package com.workFlow.controller;

import com.workFlow.payload.GlobalResponse;
import com.workFlow.repository.UserRepository;
import com.workFlow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<GlobalResponse> createUser (@RequestBody Map<String,Object> requestBody, Principal principal){
        GlobalResponse response=userService.createUser(requestBody, principal);
        return new ResponseEntity<>(response, (HttpStatusCode) response.getHttpStatus());
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers (@RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "5") int size,
                                       @RequestParam(defaultValue = "userId") String sortBy,
                                       @RequestParam(defaultValue = "asc") String sortOrder, Principal principal){
        return userService.getUsers(page, size, sortBy, sortOrder, principal);
    }

    @GetMapping("/managerDropdown")
    public List<Map<String,Object>> getAllManagerByDepartmentId(@RequestParam int departmentId,Principal principal){
        return userService.getAllManager(departmentId,principal);
    }



}
