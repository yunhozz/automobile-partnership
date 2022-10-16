package com.automobilepartnership.domain.notification.service;

import com.automobilepartnership.api.dto.notification.NotificationRequestDto;
import com.automobilepartnership.common.ErrorCode;
import com.automobilepartnership.domain.member.service.exception.MemberNotFoundException;
import com.automobilepartnership.domain.notification.service.exception.NotificationNotFoundException;
import com.automobilepartnership.domain.member.persistence.Member;
import com.automobilepartnership.domain.member.persistence.MemberRepository;
import com.automobilepartnership.domain.notification.dto.NotificationResponseDto;
import com.automobilepartnership.domain.notification.persistence.EmitterRepository;
import com.automobilepartnership.domain.notification.persistence.Notification;
import com.automobilepartnership.domain.notification.persistence.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;
    private final EmitterRepository emitterRepository;

    @Transactional
    public Long sendToClientAndSave(Long senderId, Long receiverId, NotificationRequestDto notificationRequestDto) {
        Member sender = memberRepository.getReferenceById(senderId);
        Member receiver = memberRepository.findById(receiverId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        Notification notification = Notification.builder()
                .sender(sender)
                .receiver(receiver)
                .message(notificationRequestDto.getMessage())
                .redirectUrl(notificationRequestDto.getRedirectUrl())
                .build();

        emitterRepository.findEmittersByUserId(String.valueOf(receiverId))
                .forEach((emitterId, emitter) -> {
                    emitterRepository.saveEvent(emitterId, notification);
                    sendToClient(emitter, String.valueOf(receiverId), new NotificationResponseDto(notification));
        });

        return notificationRepository.save(notification).getId();
    }

    @Transactional
    public void delete(Long notificationId) {
        Notification notification = findNotification(notificationId);
        notificationRepository.delete(notification);
    }

    @Transactional(readOnly = true)
    public List<NotificationResponseDto> findNotificationDtoList() {
        return notificationRepository.findAll().stream()
                .map(NotificationResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    private Notification findNotification(Long notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException(ErrorCode.NOTIFICATION_NOT_FOUND));
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
}