package com.automobilepartnership.domain.counsel.persistence;

import com.automobilepartnership.common.BaseTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String counselId; // 상담 pk 값

    private String originalName; // 기존 파일 이름

    private String saveName; // uuid + 파일 이름

    private String path; // 파일 경로

    public Image(String originalName, String saveName, String path) {
        this.originalName = originalName;
        this.saveName = saveName;
        this.path = path;
    }

    public void uploadOnCounsel(String counselId) {
        this.counselId = counselId;
    }
}