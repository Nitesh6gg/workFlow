package com.workflow.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface NotificationService {

    SseEmitter getNotifications(String username);

    void notifyUser(String username, String message);

    void notifyAllUsers(String message);
}
