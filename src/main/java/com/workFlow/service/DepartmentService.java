package com.workFlow.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface DepartmentService {

    List<Map<String, Object>> getAllDepartments(Principal principal);
}
