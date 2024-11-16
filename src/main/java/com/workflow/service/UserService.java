package com.workflow.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import com.workflow.dto.request.CreateUserDTO;
import com.workflow.payload.MessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    MessageResponse createUser(CreateUserDTO requestDto, Principal principal);

    Page<?> getAllUsers(Pageable pageable,Principal principal);

    List<Map<String,Object>> getAllUsersDropdown(Principal principal);

    Map<String, Object> getUserProfileDetails(Principal principal);

    List<Map<String, Object>> getAllManager(int departmentId,Principal principal);

    MessageResponse uploadImage(MultipartFile file, Principal principal);


}
