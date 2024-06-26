package com.workFlow.service;

import com.workFlow.payload.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface SessionService {

    MessageResponse invalidateSession(String sessionId);

    public List<String> getActiveSessions();

    public void logSessionDetails(HttpServletRequest request);

    public void saveActivityLog(String username,HttpServletRequest request);


}
