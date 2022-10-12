package com.automobilepartnership.domain.counsel.persistence;

import com.automobilepartnership.common.BaseInfo;
import com.automobilepartnership.common.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private BaseInfo baseInfo;

    private int count; // 상담 횟수

    public Employee(String name, int age, String residence) {
        baseInfo = new BaseInfo(name, age, residence);
    }

    public void addCount() {
        count++;
    }
}