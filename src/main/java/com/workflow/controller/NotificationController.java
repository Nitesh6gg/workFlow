package com.workflow.controller;

import com.workflow.service.NotificationService;
import com.workflow.serviceimpl.SSEService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("api/sse/notification")
@CrossOrigin("*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private SSEService sseService;

    @PostMapping("/send-notification")
    public void sendNotification(@RequestBody String notification) {
        sseService.sendNotification(notification);
    }

    @GetMapping("/get-notifications")
    public Flux<ServerSentEvent<String>> events() {
        return sseService.getEvents();
    }



    @GetMapping("get")
    public SseEmitter getNotifications(@RequestParam String username){
        return notificationService.getNotifications(username);
    }

    @PostMapping("/send")
    public void notifyUser(@RequestBody String username,String message){
        notificationService.notifyUser(username,message);
    }

    @PostMapping("/broadcast")
    public void notifyAllUsers(@RequestBody String message){
         notificationService.notifyAllUsers(message);
    }

}
