package com.workFlow.controller;

import com.workFlow.dto.request.LoginRequest;
import com.workFlow.dto.response.LoginResponse;
import com.workFlow.entity.User;
import com.workFlow.helper.UserHelper;
import com.workFlow.payload.GlobalResponse;
import com.workFlow.repository.ActivityRepository;
import com.workFlow.repository.UserRepository;
import com.workFlow.service.SessionService;
import com.workFlow.serviceImpl.UserDetailsServiceImpl;
import com.workFlow.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth") 
@CrossOrigin("*")
public class AuthController { 
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private UserHelper userHelper;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private ActivityRepository activityRepo;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AuthenticationManager authManager;

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
	
	@PostMapping("/login")
	public ResponseEntity<?> logIn(@RequestBody LoginRequest request,HttpServletRequest req) {
	    try {
	        Optional<User> email = userRepo.findByEmail(request.getUsername());// Check if the user's email is registered
	        if (email.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GlobalResponse("This Email is not registered with us", null, HttpStatus.UNAUTHORIZED));
	        }else {
	        	 // Authenticate user
		        Authentication authenticate = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		       
		        SecurityContextHolder.getContext().setAuthentication(authenticate);

		        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(authenticate.getName());

		        String jwtToken = jwtUtil.getToken(userDetails);//generate token

				sessionService.saveActivityLog(userDetails.getUsername(),req);//saving session

				String userRole=userHelper.getUserType(userDetails.getUsername());//find userRole by username(email)

				String username=userHelper.getUsername(userDetails.getUsername());// find username by email

				return ResponseEntity.ok(new LoginResponse(jwtToken, username,userDetails.getUsername(),userRole)); // Return token and username in the response
	        }
	    } catch (BadCredentialsException e) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new GlobalResponse("Invalid Password",null,HttpStatus.UNAUTHORIZED));
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new GlobalResponse("Something went wrong", e.getMessage()));
	    }
	}

} 
