package com.automobilepartnership.domain.counsel.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CounselQueryDto {

    // counsel
    private Long id;
    private String type;
    private String title;
    private boolean isResolved;
    private LocalDateTime createdDate;

    // member
    private Long userId;
    private String name;

    @QueryProjection
    public CounselQueryDto(Long id, String type, String title, boolean isResolved, LocalDateTime createdDate, Long userId, String name) {
        this.id = id;
        this.type = type;
        this.title = title;
        this.isResolved = isResolved;
        this.createdDate = createdDate;
        this.userId = userId;
        this.name = name;
    }
}