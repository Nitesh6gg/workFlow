package com.workFlow.service;

import org.apache.poi.ss.formula.functions.T;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface TaskService {

    List<Map<String,Object>> getAllTeamLeaders(Principal principal);
}
