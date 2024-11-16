package com.workflow.serviceimpl;

import com.workflow.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

    private final ConcurrentHashMap<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    @Override
    public SseEmitter getNotifications(String username) {
        SseEmitter emitter = new SseEmitter();
        sseEmitters.put(username, emitter);

        emitter.onCompletion(() -> sseEmitters.remove(username));
        emitter.onTimeout(() -> sseEmitters.remove(username));
        return emitter;
    }

    @Override
    public void notifyUser(String username,String message){
        SseEmitter emitter = sseEmitters.get(username);
        if(emitter != null){
            try{
                emitter.send(message);
            }catch (Exception e){
                log.warn("Error sending message", e);
                emitter.completeWithError(e);
                sseEmitters.remove(username);
            }
        }
    }

   @Override
    public void notifyAllUsers(String message) {
        sseEmitters.forEach((username, emitter) -> {
            try {
                emitter.send(message);
            } catch (IOException e) {
                emitter.completeWithError(e);
                sseEmitters.remove(username);
            }
        });
    }


}
