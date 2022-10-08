package com.automobilepartnership.domain.notification.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NotificationQueryDto {

    // notification
    private Long id;
    private String message;
    private String redirectUrl;
    private LocalDateTime createdDate;

    // sender
    private Long senderId;
    private String name;
    private String imageUrl;

    @QueryProjection
    public NotificationQueryDto(Long id, String message, String redirectUrl, LocalDateTime createdDate, Long senderId, String name, String imageUrl) {
        this.id = id;
        this.message = message;
        this.redirectUrl = redirectUrl;
        this.createdDate = createdDate;
        this.senderId = senderId;
        this.name = name;
        this.imageUrl = imageUrl;
    }
}