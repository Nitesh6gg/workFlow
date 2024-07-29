package com.workFlow.helper;

import com.workFlow.dto.request.CreateUserDTO;
import com.workFlow.entity.User;
import com.workFlow.payload.GlobalResponse;
import com.workFlow.payload.MessageResponse;
import com.workFlow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Component
public class UserHelper {

    private final UserRepository userRepo;

    @Autowired
    public UserHelper(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public int getUserType(Principal principal) {
        return userRepo.findRoleByEmail(principal.getName());
    }

    public String getUserType(String username) {
        return userRepo.findRoleTypeByEmail(username);
    }

    public String getUserName(Principal principal) {
        return userRepo.findUsernameByPrincipal(principal.getName());
    }

    public String getUsername(String username) {
        return userRepo.findUsernameByPrincipal(username);
    }

    public int getUserId(Principal principal) {
        return userRepo.findUserIdByPrincipal(principal.getName());
    }



    public GlobalResponse checkUserDetails(Map<String, Object> request){
        Optional<User> username = userRepo.findByUsername(request.get("username").toString());
        if (username.isPresent()) {
            return new GlobalResponse("username already in use", HttpStatus.BAD_REQUEST);
        }

        Optional<User> email = userRepo.findByEmail(request.get("email").toString());
        if (email.isPresent()) {
            return new GlobalResponse("Email id already registered", HttpStatus.BAD_REQUEST);
        }

        Optional<User> phone = userRepo.findByPhone(request.get("phone").toString());
        if (phone.isPresent()) {
            return new GlobalResponse("Phone no. already registered", HttpStatus.BAD_REQUEST);
        }

        String password = request.get("password").toString();
        if (password == null || password.isEmpty()) {
            return new GlobalResponse("Password cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        return null;
    }


    public MessageResponse checkUserDetails(CreateUserDTO request){
        Optional<User> username = userRepo.findByUsername(request.getUsername());
        if (username.isPresent()) {
            return new MessageResponse("username already in use", HttpStatus.BAD_REQUEST);
        }

        Optional<User> email = userRepo.findByEmail(request.getEmail());
        if (email.isPresent()) {
            return new MessageResponse("Email id already registered", HttpStatus.BAD_REQUEST);
        }

        Optional<User> phone = userRepo.findByPhone(request.getPhone());
        if (phone.isPresent()) {
            return new MessageResponse("Phone no. already registered", HttpStatus.BAD_REQUEST);
        }

        String password = request.getPassword();
        if (password == null || password.isEmpty()) {
            return new MessageResponse("Password cannot be null or empty", HttpStatus.BAD_REQUEST);
        }
        return null;
    }



}