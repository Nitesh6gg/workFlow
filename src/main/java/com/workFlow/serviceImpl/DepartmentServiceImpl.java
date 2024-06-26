package com.workFlow.serviceImpl;

import com.workFlow.repository.DepartmentRepository;
import com.workFlow.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.util.List;
import java.util.Map;
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepo;
    @Override
    public List<Map<String, Object>> getAllDepartments(Principal principal) {
        return departmentRepo.getAllDepartment();
    }
}
