package com.workflow.serviceImpl;

import com.workflow.dto.request.CreateUserDTO;
import com.workflow.entity.*;
import com.workflow.helper.UserHelper;
import com.workflow.payload.MessageResponse;
import com.workflow.repository.*;
import com.workflow.service.ImageService;
import com.workflow.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserHelper userHelper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepo;
    private final PositionRepository positionRepo;
    private final DepartmentRepository departmentRepo;
    private final RoleRepository roleRepo;
    private final UserRoleRepository userRoleRepo;
    private final ImageService imageService;

    @Override
    @Transactional
    public MessageResponse createUser(CreateUserDTO requestDto, Principal principal) {
        try {
            int userType = userHelper.getUserType(principal);
            if (userType==3) {
                return new MessageResponse("You are not authorized to perform this action", HttpStatus.UNAUTHORIZED);
            }
            // Check username, email, phone not already registered
            MessageResponse error = userHelper.checkUserDetails(requestDto);
            if (error != null) return error;

            User newUser = new User();
            newUser.setUuid(UUID.randomUUID().toString());
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
                return new MessageResponse("User role not found", HttpStatus.BAD_REQUEST);
            }
            Role role = roleOptional.get();

            UserRole newRole = new UserRole();
            newRole.setUserId(savedUser.getUserId());
            newRole.setRoleId(role.getRoleId());
            newRole.setCreatedBy(userHelper.getUserName(principal));
            newRole.setCreatedON(String.valueOf(LocalDateTime.now()));

            if(userType==2){
                Optional<Position> positionOptional = positionRepo.findById(requestDto.getPosition());
                if (positionOptional.isEmpty()) return new MessageResponse("Position not found", HttpStatus.BAD_REQUEST);
                Position position = positionOptional.get();

                Optional<Department> departmentOptional = departmentRepo.findById(requestDto.getDepartment());
                if (departmentOptional.isEmpty()) return new MessageResponse("Department not found", HttpStatus.BAD_REQUEST);
                Department department = departmentOptional.get();

                newRole.setPositionId(position);
                newRole.setDepartmentId(department);
            }
            userRoleRepo.save(newRole);
            return new MessageResponse("Registration successful", HttpStatus.CREATED);
        } catch (Exception e) {
            return new MessageResponse("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public Page<?> getAllUsers(Pageable pageable,Principal principal) {

        int userType = userHelper.getUserType(principal);
        if (userType == 1) {
            return userRepo.findAllForSuperAdmin(pageable);
        } else if (userType == 2) {
            return userRepo.findAllForAdmin(userHelper.getUserName(principal),pageable );
        } else return Page.empty();
    }

    @Override
    public List<Map<String, Object>> getAllUsersDropdown(Principal principal) {
        return userRepo.findAllUsersDropdown(userHelper.getUserName(principal));
    }

    @Override
    public Map<String, Object> getUserProfileDetails(Principal principal) {
        return userRepo.findProfileDetails(userHelper.getUserName(principal));
    }

    @Override
    public MessageResponse uploadImage(MultipartFile file, Principal principal) {
        Optional<User> byUsername = userRepo.findByUsername(userHelper.getUserName(principal));
        if (byUsername.isPresent()) {
            User user = byUsername.get();
            String uuid = user.getUuid();
            user.setImageUrl(imageService.processImageAndGenerateUrl(file));
            userRepo.save(user);
            return new MessageResponse("Image uploaded", HttpStatus.OK);
        } else {
            return new MessageResponse("User not found", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<Map<String, Object>> getAllManager(int departmentId,Principal principal) {
        return userRepo.getAllManager(userHelper.getUserName(principal), departmentId);
    }


}
