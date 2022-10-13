package com.automobilepartnership.domain.counsel.service;

import com.automobilepartnership.common.ErrorCode;
import com.automobilepartnership.domain.counsel.service.exception.CounselNotFoundException;
import com.automobilepartnership.domain.counsel.persistence.Counsel;
import com.automobilepartnership.domain.counsel.persistence.CounselRepository;
import com.automobilepartnership.domain.counsel.persistence.Image;
import com.automobilepartnership.domain.counsel.persistence.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final CounselRepository counselRepository;

    @Value("${spring.servlet.multipart.location}")
    private String fileDir;

    @Transactional
    public void saveAndUpload(Long counselId, MultipartFile[] files) throws IOException {
        Counsel counsel = counselRepository.findById(counselId)
                .orElseThrow(() -> new CounselNotFoundException(ErrorCode.COUNSEL_NOT_FOUND));

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String uuid = UUID.randomUUID().toString();
                String originalName = file.getOriginalFilename(); // 원래 파일 이름 추출
                String extension = originalName.substring(originalName.indexOf(".")); // 확장자 추출

                String saveName = uuid + extension;
                String path = fileDir + saveName;
                Image image = Image.builder()
                        .counsel(counsel)
                        .originalName(originalName)
                        .saveName(saveName)
                        .path(path)
                        .build();

                file.transferTo(new File(saveName));
                imageRepository.save(image);
            }
        }
    }
}