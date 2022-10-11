package com.automobilepartnership.domain.counsel.dto;

import com.automobilepartnership.domain.counsel.persistence.Counsel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CounselResponseDto {

    private Long id;
    private Long userId;
    private String employeeId;
    private String type;
    private String title;
    private String detail;
    private Boolean isResolved;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;

    public CounselResponseDto(Counsel counsel) {
        id = counsel.getId();
        userId = counsel.getMember().getId();
        employeeId = counsel.getEmployeeId();
        type = counsel.getCounselType().getDesc();
        title = counsel.getTitle();
        detail = counsel.getDetail();
        isResolved = counsel.isResolved();
        createdDate = counsel.getCreatedDate();
        lastModifiedDate = counsel.getLastModifiedDate();
    }
}