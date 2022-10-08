package com.automobilepartnership.domain.notification.dto;

import com.automobilepartnership.domain.notification.persistence.Notification;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NotificationResponseDto {

    private Long id;
    private Long senderId;
    private Long receiverId;
    private String message;
    private String redirectUrl;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public NotificationResponseDto(Notification notification) {
        id = notification.getId();
        senderId = notification.getSender().getId();
        receiverId = notification.getReceiver().getId();
        message = notification.getMessage();
        redirectUrl = notification.getRedirectUrl();
        createdDate = notification.getCreatedDate();
        lastModifiedDate = notification.getLastModifiedDate();
    }
}