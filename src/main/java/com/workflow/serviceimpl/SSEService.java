package com.workflow.serviceimpl;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
@Component
public class SSEService {

    private final Sinks.Many<String> sink;

    public SSEService() {
        this.sink = Sinks.many().multicast().onBackpressureBuffer();
    }
    //take notification msg from admin
    public void sendNotification(String notification) {
        sink.tryEmitNext(notification);
    }
    //send notifications to all subcription
    public Flux<ServerSentEvent<String>> getEvents() {
        return sink.asFlux().map(notification -> ServerSentEvent.builder(notification).build()).retry();
    }
}
