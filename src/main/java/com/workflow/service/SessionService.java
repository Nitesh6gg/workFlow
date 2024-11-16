package com.workflow.service;

import com.workflow.payload.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface SessionService {

    MessageResponse invalidateSession(String sessionId);

    public List<String> getActiveSessions();

    public void logSessionDetails(HttpServletRequest request);

    public void saveActivityLog(String username,HttpServletRequest request);


}
