package com.automobilepartnership.domain.notification.persistence;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository {

    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> events = new ConcurrentHashMap<>();

    @Override
    public SseEmitter saveEmitter(String emitterId, SseEmitter emitter) {
        emitters.put(emitterId, emitter);
        return emitter;
    }

    @Override
    public void saveEvent(String eventId, Object event) {
        events.put(eventId, event);
    }

    @Override
    public Map<String, SseEmitter> findEmittersByUserId(String userId) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(userId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, Object> findEventsByUserId(String userId) {
        return events.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(userId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void deleteEmitter(String emitterId) {
        emitters.keySet().forEach(key -> {
            if (key.equals(emitterId)) {
                emitters.remove(key);
            }
        });
    }

    @Override
    public void deleteEvent(String eventId) {
        events.keySet().forEach(key -> {
            if (key.equals(eventId)) {
                events.remove(key);
            }
        });
    }
}