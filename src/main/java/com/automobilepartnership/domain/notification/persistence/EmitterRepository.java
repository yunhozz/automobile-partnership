package com.automobilepartnership.domain.notification.persistence;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {

    SseEmitter saveEmitter(String emitterId, SseEmitter emitter);
    void saveEvent(String eventId, Object event);
    Map<String, SseEmitter> findEmittersByUserId(String userId);
    Map<String, Object> findEventsByUserId(String userId);
    void deleteEmitter(String emitterId);
    void deleteEvent(String eventId);
}