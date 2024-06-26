package com.workFlow.service;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface PositionService {

    List<Map<String, Object>> getAllPosition(Principal principal);
}
