package com.workflow.controller;

import com.workflow.dto.request.LoginRequest;
import com.workflow.dto.response.LoginResponse;
import com.workflow.entity.User;
import com.workflow.helper.UserHelper;
import com.workflow.repository.ActivityRepository;
import com.workflow.repository.UserRepository;
import com.workflow.service.SessionService;
import com.workflow.serviceimpl.UserDetailsServiceImpl;
import com.workflow.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@AllArgsConstructor
@RequestMapping("/api/auth") 
@CrossOrigin("*")
public class AuthController { 

	private final UserRepository userRepo;
	private final UserHelper userHelper;
	private final SessionService sessionService;
	private final ActivityRepository activityRepo;
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	private final JwtUtil jwtUtil;
	private final AuthenticationManager authManager;
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/login")
	public ResponseEntity<?> logIn(@RequestBody LoginRequest request,HttpServletRequest req) throws BadRequestException{
	    try {
	        Optional<User> email = userRepo.findByEmail(request.getUsername());// Check if the user's email is registered
	        if (email.isEmpty()) {
				throw new BadRequestException("Email is not registered");
	        }else {
	        	 // Authenticate user
		        Authentication authenticate = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		       
		        SecurityContextHolder.getContext().setAuthentication(authenticate);

		        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(authenticate.getName());

		        String jwtToken = jwtUtil.getToken(userDetails);//generate token

				sessionService.logSessionDetails(req);

				sessionService.saveActivityLog(userDetails.getUsername(),req);//saving session

				String userRole=userHelper.getUserType(userDetails.getUsername());//find userRole by username(email)

				String username=userHelper.getUsername(userDetails.getUsername());// find username by email

				return ResponseEntity.ok(new LoginResponse(jwtToken, username,userDetails.getUsername(),userRole)); // Return token and username in the response
	        }
	    } catch (BadCredentialsException e) {
			throw new BadRequestException("Invalid Password");
	    } catch (RuntimeException e) {
			LOGGER.error("Unexpected error occurred during login for user: {}", request.getUsername(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong");
		}
	}

} 
