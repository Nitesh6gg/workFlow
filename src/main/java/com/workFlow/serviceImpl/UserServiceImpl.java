package com.workFlow.serviceImpl;

import com.workFlow.dto.request.CreateUserDTO;
import com.workFlow.entity.*;
import com.workFlow.helper.PaginationHelper;
import com.workFlow.helper.UserHelper;
import com.workFlow.payload.GlobalResponse;
import com.workFlow.repository.*;
import com.workFlow.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserHelper userHelper;
    private final PaginationHelper paginationHelper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final PositionRepository positionRepo;
    private final DepartmentRepository departmentRepo;
    private final RoleRepository roleRepo;
    private final UserRoleRepository userRoleRepo;

    @Autowired
    public UserServiceImpl(UserHelper userHelper, PaginationHelper paginationHelper, BCryptPasswordEncoder passwordEncoder,
                           UserRepository userRepo, PositionRepository positionRepo, DepartmentRepository departmentRepo, RoleRepository roleRepo, UserRoleRepository userRoleRepo) {
        this.userHelper = userHelper;
        this.paginationHelper = paginationHelper;
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.positionRepo = positionRepo;
        this.departmentRepo = departmentRepo;
        this.roleRepo = roleRepo;
        this.userRoleRepo = userRoleRepo;
    }

    @Override
    @Transactional
    public GlobalResponse createAdmin(Map<String, Object> requestBody, Principal principal) {
        try {
            int userType = userHelper.getUserType(principal);
            if (userType != 1) {
                return new GlobalResponse("You are not authorized to perform this action", HttpStatus.UNAUTHORIZED);
            }
            //check username,email,phone not already registered
            GlobalResponse error=userHelper.checkUserDetails(requestBody);
            if(error!=null) return error;

            User newUser = new User();
            newUser.setUsername(requestBody.get("username").toString());
            newUser.setEmail(requestBody.get("email").toString());
            newUser.setPhone(requestBody.get("phone").toString());
            newUser.setCreatedBy(userHelper.getUserName(principal));
            newUser.setFirstName(requestBody.get("firstName").toString());
            newUser.setLastName(requestBody.get("lastName").toString());
            newUser.setCreatedON(String.valueOf(new Date()));
            newUser.setPassword(passwordEncoder.encode(requestBody.get("password").toString()));

            User savedUser = userRepo.save(newUser);

            Optional<Role> roleOptional = roleRepo.findByRoleType("ROLE_ADMIN");
            if (!roleOptional.isPresent()) {
                return new GlobalResponse("Admin role not found", HttpStatus.BAD_REQUEST);
            }
            Role role = roleOptional.get();

            UserRole userRole = new UserRole();
            userRole.setRoleId(role.getRoleId());
            userRole.setUserId(savedUser.getUserId());
            userRole.setCreatedBy(userHelper.getUserName(principal));
            userRole.setCreatedON(String.valueOf(new Date()));
            userRoleRepo.save(userRole);

            return new GlobalResponse("Registration successful", HttpStatus.CREATED);
        } catch (Exception e) {
            return new GlobalResponse("Something went wrong!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public GlobalResponse createUser(CreateUserDTO requestDto, Principal principal) {
        try {
            int userType = userHelper.getUserType(principal);
            if (userType != 2) {
                return new GlobalResponse("You are not authorized to perform this action", HttpStatus.UNAUTHORIZED);
            }

            // Check username, email, phone not already registered
            GlobalResponse error = userHelper.checkUserDetails(requestDto);
            if (error != null) return error;

            User newUser = new User();
            newUser.setUsername(requestDto.getUsername());
            newUser.setEmail(requestDto.getEmail());
            newUser.setPhone(requestDto.getPhone());
            newUser.setCreatedBy(userHelper.getUserName(principal));
            newUser.setFirstName(requestDto.getFirstName());
            newUser.setLastName(requestDto.getLastName());
            newUser.setCreatedON(String.valueOf(LocalDateTime.now()));
            newUser.setPassword(passwordEncoder.encode(requestDto.getPassword()));

            User savedUser = userRepo.save(newUser);

            Optional<Role> roleOptional = roleRepo.findById(requestDto.getRole());
            if (roleOptional.isEmpty()) {
                return new GlobalResponse("User role not found", HttpStatus.BAD_REQUEST);
            }
            Role role = roleOptional.get();

            Optional<Position> positionOptional = positionRepo.findById(requestDto.getPosition());
            if (positionOptional.isEmpty()) {
                return new GlobalResponse("Position not found", HttpStatus.BAD_REQUEST);
            }
            Position position = positionOptional.get();

            Optional<Department> departmentOptional = departmentRepo.findById(requestDto.getDepartment());
            if (departmentOptional.isEmpty()) {
                return new GlobalResponse("Department not found", HttpStatus.BAD_REQUEST);
            }
            Department department = departmentOptional.get();

            UserRole newRole = new UserRole();
            newRole.setUserId(savedUser.getUserId());
            newRole.setRoleId(role.getRoleId());
            newRole.setPositionId(position);
            newRole.setDepartmentId(department);
            newRole.setCreatedBy(userHelper.getUserName(principal));
            newRole.setCreatedON(String.valueOf(LocalDateTime.now()));
            userRoleRepo.save(newRole);

            return new GlobalResponse("Registration successful", HttpStatus.CREATED);
        } catch (Exception e) {
            return new GlobalResponse("Something went wrong!", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public Page<?> getAllUsers(Pageable pageable,Principal principal) {

        int userType = userHelper.getUserType(principal);

        if (userType == 1) {
            return userRepo.findAllForSuperAdmin(pageable);
        } else if (userType == 2) {
            return userRepo.findAllForAdmin(userHelper.getUserName(principal),pageable );
        } else {
            return Page.empty();
        }

    }

    @Override
    public List<Map<String, Object>> getAllManager(int departmentId,Principal principal) {
        //String username=userHelper.getUserName(principal);
        List<Map<String, Object>> allManager = userRepo.getAllManager(departmentId);
       /* if(allManager.isEmpty()) {
            Map<String, Object> response=new HashMap<String, Object>();
            response.put("message","No manager found");
            return Collections.singletonList(response);
        }*/
        return userRepo.getAllManager(departmentId);
    }


}
