package com.workFlow.serviceImpl;

import com.workFlow.entity.ActivityLog;
import com.workFlow.payload.MessageResponse;
import com.workFlow.repository.ActivityRepository;
import com.workFlow.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private ActivityRepository activityRepo;


    @Override
    public MessageResponse invalidateSession(String sessionId) {
        SessionInformation sessionInformation = sessionRegistry.getSessionInformation(sessionId);
        if (sessionInformation != null) {
            sessionInformation.expireNow();
            return new MessageResponse("Session Invalidate Successfully", HttpStatus.OK);
        }else{
            return new MessageResponse("Session Not Found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<String> getActiveSessions() {
        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        List<String> activeSessions = new ArrayList<>();

        for (Object principal : allPrincipals) {
            List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, false);
            for (SessionInformation session : sessions) {
                System.out.println(principal.toString());
                activeSessions.add("User: " + principal + " - Session ID: " + session.getSessionId() + " - Last Request: " + session.getLastRequest());
            }
        }
        return activeSessions;
    }

    public void logSessionDetails(HttpServletRequest request) {
        System.out.println("authType: " + request.getAuthType());
        System.out.println("remoteAddr: " + request.getRemoteAddr());
        System.out.println("remoteHost: " + request.getRemoteHost());
        System.out.println("remotePort: " + request.getRemotePort());
        System.out.println("sessionId: " + request.getSession().getId());
        System.out.println("sessionTime: " + request.getSession().getLastAccessedTime());
    }

    @Override
    public void saveActivityLog(String username, HttpServletRequest request) {
        ActivityLog log = new ActivityLog();
        log.setUsername(username);
        log.setSessionId(request.getSession().getId());
        log.setIpAddress(request.getRemoteAddr());
        log.setLastLogin(String.valueOf(new Date()));
        activityRepo.save(log);
    }


}
