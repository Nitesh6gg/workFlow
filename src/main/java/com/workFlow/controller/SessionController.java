package com.workFlow.controller;

import com.workFlow.payload.MessageResponse;
import com.workFlow.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class SessionController {

    @Autowired
    private SessionRegistry sessionRegistry;
    @Autowired
    private SessionService sessionService;

    @GetMapping("/active-sessions")
    public ResponseEntity<?> getActiveSessions() {
        List<String> activeSessions = sessionService.getActiveSessions();
        return ResponseEntity.ok(activeSessions);
    }

    @PostMapping("/invalidate-session")
    public ResponseEntity<MessageResponse> invalidateSession(@RequestParam String sessionId) {
        MessageResponse response=sessionService.invalidateSession(sessionId);
        return new ResponseEntity<>(response, (HttpStatusCode) response.getHttpStatus());
    }

}
