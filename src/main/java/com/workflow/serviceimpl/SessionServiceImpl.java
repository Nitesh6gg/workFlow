package com.workflow.serviceimpl;

import com.workflow.entity.ActivityLog;
import com.workflow.payload.MessageResponse;
import com.workflow.repository.ActivityRepository;
import com.workflow.service.SessionService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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
       /* List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        List<String> activeSessions = new ArrayList<>();

        List<Object> allPrincipalss = sessionRegistry.getAllPrincipals();
        for (Object principal : allPrincipalss) {
            System.out.println("Principal is : " + principal);
        }

        for (Object principal : allPrincipals) {
            List<SessionInformation> sessions = sessionRegistry.getAllSessions(principal, false);
            for (SessionInformation session : sessions) {
                System.out.println(principal.toString());
                activeSessions.add("User: " + principal + " - Session ID: " + session.getSessionId() + " - Last Request: " + session.getLastRequest());
            }
        }
        return activeSessions;*/
        List<String> users = new ArrayList<>();
        List<Object> principals = sessionRegistry.getAllPrincipals();

        for (Object principal : principals) {
            List<SessionInformation> sessionsInfo = sessionRegistry.getAllSessions(principal, false);
            if (!sessionsInfo.isEmpty()) {
                users.add(((UserDetailsImpl) principal).getUsername());
            }
        }
        return users;
    }
    

   /* public void logSessionDetails(HttpServletRequest request) {
        System.out.println("authType: " + request.getAuthType());
        System.out.println("remoteAddr: " + request.getRemoteAddr());
        System.out.println("remoteHost: " + request.getRemoteHost());
        System.out.println("remotePort: " + request.getRemotePort());
        System.out.println("sessionId: " + request.getSession().getId());
        System.out.println("sessionTime: " + request.getSession().getLastAccessedTime());
    }*/

    @Override
    public void saveActivityLog(String username, HttpServletRequest request) {
        ActivityLog log = new ActivityLog();
        log.setUsername(username);
        log.setSessionId(request.getSession().getId());
        log.setIpAddress(request.getRemoteAddr());
        log.setLastLogin(String.valueOf(new Date()));
        activityRepo.save(log);
    }

    public void logSessionDetails(HttpServletRequest request) {
        String requestSessionId = request.getSession().getId();
        System.out.println("Request Session ID: " + requestSessionId);


        List<SessionInformation> sessions = sessionRegistry.getAllSessions(SecurityContextHolder.getContext().getAuthentication().getPrincipal(), false);
        for (SessionInformation session : sessions) {
            System.out.println("SessionRegistry Session ID: " + session.getSessionId());
        }
    }



}
