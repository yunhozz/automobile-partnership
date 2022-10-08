package com.automobilepartnership.domain.notification.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long>, NotificationCustomRepository {

    @Query("select n.redirectUrl from Notification n where n.id = :id")
    String findRedirectUrlById(@Param("id") Long notificationId);
}