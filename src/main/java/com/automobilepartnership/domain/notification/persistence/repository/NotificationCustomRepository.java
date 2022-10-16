package com.automobilepartnership.domain.notification.persistence.repository;

import com.automobilepartnership.domain.notification.dto.NotificationQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NotificationCustomRepository {

    Page<NotificationQueryDto> findNotificationDtoListByUserId(Long userId, Pageable pageable);
}