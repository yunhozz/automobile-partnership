package com.automobilepartnership.domain.counsel.persistence;

import com.automobilepartnership.common.BaseTime;
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
public class Image extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "counsel_id")
    private Counsel counsel;

    private String originalName; // 기존 파일 이름

    private String saveName; // uuid + 파일 이름

    private String path; // 파일 경로

    @Builder
    private Image(Counsel counsel, String originalName, String saveName, String path) {
        this.counsel = counsel;
        this.originalName = originalName;
        this.saveName = saveName;
        this.path = path;
    }
}