package com.workFlow.serviceImpl;

import com.workFlow.entity.Role;
import com.workFlow.entity.User;
import com.workFlow.entity.UserRole;
import com.workFlow.helper.PaginationHelper;
import com.workFlow.helper.UserHelper;
import com.workFlow.payload.GlobalResponse;
import com.workFlow.repository.RoleRepository;
import com.workFlow.repository.UserRepository;
import com.workFlow.repository.UserRoleRepository;
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
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserHelper userHelper;
    private final PaginationHelper paginationHelper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final UserRoleRepository userRoleRepo;

    @Autowired
    public UserServiceImpl(UserHelper userHelper,PaginationHelper paginationHelper,BCryptPasswordEncoder passwordEncoder,
                           UserRepository userRepo,RoleRepository roleRepo,UserRoleRepository userRoleRepo) {
        this.userHelper = userHelper;
        this.paginationHelper = paginationHelper;
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
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
    public GlobalResponse createUser(Map<String, Object> requestBody, Principal principal) {
        try {
            int userType = userHelper.getUserType(principal);
            if (userType !=2) {
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

            Optional<Role> roleOptional = roleRepo.findByRoleType("ROLE_USER");
            if (!roleOptional.isPresent()) {
                return new GlobalResponse("User role not found", HttpStatus.BAD_REQUEST);
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
    public ResponseEntity<?> getUsers(int page, int size, String sortBy, String sortOrder, Principal principal) {
        Pageable pageable = PaginationHelper.createPageable(page, size, sortBy, sortOrder);

        int userType = userHelper.getUserType(principal);
        Page<List<Map<String, Object>>> userPage;

        if (userType == 1) {
            userPage = userRepo.findAllForSuperAdmin(pageable);
        } else if (userType == 2) {
            userPage = userRepo.findAllForAdmin(userHelper.getUserName(principal),pageable );
        } else {
            return new ResponseEntity<>(new GlobalResponse("You are not authorized to perform this action", HttpStatus.UNAUTHORIZED), HttpStatus.UNAUTHORIZED);
        }

        List<List<Map<String, Object>>> users = userPage.getContent();
        Map<String, Object> response = PaginationHelper.createResponse(userPage, users);
        return ResponseEntity.ok(response);
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
