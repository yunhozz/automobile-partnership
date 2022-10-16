package com.automobilepartnership.domain.notification.persistence;

import com.automobilepartnership.common.BaseTime;
import com.automobilepartnership.domain.member.persistence.entity.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    private String message;

    private String redirectUrl;

    @Builder
    private Notification(Member sender, Member receiver, String message, String redirectUrl) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.redirectUrl = redirectUrl;
    }
}