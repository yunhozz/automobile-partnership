package com.automobilepartnership.domain.notification.persistence.repository;

import com.automobilepartnership.domain.member.persistence.QMember;
import com.automobilepartnership.domain.notification.dto.NotificationQueryDto;
import com.automobilepartnership.domain.notification.dto.QNotificationQueryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.automobilepartnership.domain.notification.persistence.QNotification.*;

@Repository
@RequiredArgsConstructor
public class NotificationCustomRepositoryImpl implements NotificationCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<NotificationQueryDto> findNotificationDtoListByUserId(Long userId, Pageable pageable) {
        QMember sender = new QMember("sender");

        List<NotificationQueryDto> notifications = queryFactory
                .select(new QNotificationQueryDto(
                        notification.id,
                        notification.message,
                        notification.redirectUrl,
                        notification.createdDate,
                        sender.id,
                        sender.baseInfo.name,
                        sender.imageUrl
                ))
                .from(notification)
                .join(notification.sender, sender)
                .where(notification.receiver.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(notification.createdDate.desc())
                .fetch();

        Long count = queryFactory
                .select(notification.count())
                .from(notification)
                .fetchOne();

        return new PageImpl<>(notifications, pageable, count);
    }
}