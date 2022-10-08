package com.automobilepartnership.domain.notification.service;

import com.automobilepartnership.domain.notification.persistence.EmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectService {

    private final EmitterRepository emitterRepository;

    private final static Long CONNECTION_TIMEOUT = 3600L * 1000; // 1 hour

    @Transactional
    public void connectWithUser(Long userId, String lastEventId) {
        String emitterId = userId + "_" + System.currentTimeMillis();
        SseEmitter emitter = new SseEmitter(CONNECTION_TIMEOUT);
        emitterRepository.saveEmitter(emitterId, emitter);

        emitter.onTimeout(() -> complete(emitterId));
        emitter.onCompletion(() -> complete(emitterId));
        sendToClient(emitter, emitterId, "EventStream Created. [user id = " + userId + "]");

        if (lastEventId != null) {
            emitterRepository.findEventsByUserId(String.valueOf(userId)).entrySet().stream()
                    .filter(entry -> entry.getKey().compareTo(lastEventId) > 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }
    }

    private void sendToClient(SseEmitter emitter, String id, Object data) {
        try {
            emitter.send(
                    SseEmitter.event()
                            .id(id)
                            .name("sse")
                            .data(data)
                            .build()
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void complete(String emitterId) {
        log.info("emitter completed");
        emitterRepository.deleteEmitter(emitterId);
    }
}