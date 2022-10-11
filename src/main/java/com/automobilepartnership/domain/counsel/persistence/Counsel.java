package com.automobilepartnership.domain.counsel.persistence;

import com.automobilepartnership.common.BaseTime;
import com.automobilepartnership.common.converter.CounselTypeConverter;
import com.automobilepartnership.domain.member.persistence.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
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
public class Counsel extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String employeeId; // Employee PK 값

    @Convert(converter = CounselTypeConverter.class)
    private CounselType counselType;

    @Column(length = 50)
    private String title;

    @Column(length = 1000)
    private String detail;

    private boolean isResolved;

    @Builder
    private Counsel(Member member, CounselType counselType, String title, String detail) {
        this.member = member;
        this.counselType = counselType;
        this.title = title;
        this.detail = detail;
    }

    public void allocateEmployee(Employee employee) {
        if (employeeId == null) {
            employeeId = String.valueOf(employee.getId());
        } else {
            throw new IllegalStateException("이미 상담사가 배정되어 있습니다.");
        }
    }

    public void updateTypeAndTitleAndDetail(String type, String title, String detail) {
        CounselTypeConverter converter = new CounselTypeConverter();
        this.counselType = converter.convertToEntityAttribute(type);
        this.title = title;
        this.detail = detail;
    }

    public void resolved(Employee employee) {
        if (!isResolved) {
            isResolved = true;
            employee.addCount(); // 상담 횟수 +1
        } else {
            throw new IllegalStateException("이미 해결 완료된 상담 내역입니다.");
        }
    }
}