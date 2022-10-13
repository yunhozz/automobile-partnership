package com.automobilepartnership.domain.counsel.dto;

import com.automobilepartnership.domain.counsel.persistence.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ImageResponseDto {

    private Long id;
    private Long counselId;
    private String originalName;
    private String saveName;
    private String path;
    private LocalDateTime createdDate;

    public ImageResponseDto(Image image) {
        id = image.getId();
        counselId = image.getCounsel().getId();
        originalName = image.getOriginalName();
        saveName = image.getSaveName();
        path = image.getPath();
        createdDate = image.getCreatedDate();
    }
}