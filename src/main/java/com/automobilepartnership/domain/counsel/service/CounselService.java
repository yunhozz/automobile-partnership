package com.automobilepartnership.domain.counsel.service;

import com.automobilepartnership.api.dto.counsel.CounselRequestDto;
import com.automobilepartnership.common.converter.CounselTypeConverter;
import com.automobilepartnership.common.ErrorCode;
import com.automobilepartnership.common.exception.AlreadyAllocatedException;
import com.automobilepartnership.common.exception.CounselNotFoundException;
import com.automobilepartnership.domain.counsel.dto.CounselResponseDto;
import com.automobilepartnership.domain.counsel.persistence.Counsel;
import com.automobilepartnership.domain.counsel.persistence.CounselRepository;
import com.automobilepartnership.domain.counsel.persistence.Image;
import com.automobilepartnership.domain.counsel.persistence.ImageRepository;
import com.automobilepartnership.domain.member.persistence.Member;
import com.automobilepartnership.domain.member.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CounselService {

    private final CounselRepository counselRepository;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long create(Long userId, List<Long> imageIds, CounselRequestDto counselRequestDto) {
        Member member = memberRepository.getReferenceById(userId);
        CounselTypeConverter converter = new CounselTypeConverter();

        Counsel counsel = Counsel.builder()
                .member(member)
                .counselType(converter.convertToEntityAttribute(counselRequestDto.getType()))
                .title(counselRequestDto.getTitle())
                .detail(counselRequestDto.getDetail())
                .build();

        counselRepository.save(counsel);

        for (Long imageId : imageIds) {
            Image image = imageRepository.getReferenceById(imageId);
            image.uploadOnCounsel(String.valueOf(counsel.getId()));
        }

        return counsel.getId();
    }

    @Transactional
    public CounselResponseDto updateTypeAndTitleAndDetail(Long counselId, String type, String title, String detail) {
        Counsel counsel = findCounsel(counselId);
        counsel.updateTypeAndTitleAndDetail(type, title, detail);

        return new CounselResponseDto(counsel);
    }

    @Transactional
    public void delete(Long counselId) {
        // 상담사가 이미 배정되어 있으면 삭제 불가
        if (counselRepository.isEmployeeIdExist(counselId)) {
            throw new AlreadyAllocatedException(ErrorCode.ALREADY_ALLOCATED);
        }
        Counsel counsel = findCounsel(counselId);
        counselRepository.delete(counsel);
    }

    public CounselResponseDto findCounselDto(Long counselId) {
        return new CounselResponseDto(findCounsel(counselId));
    }

    public List<CounselResponseDto> findCounselDtoListByEmployee(String employeeId) {
        return counselRepository.findByEmployeeId(employeeId).stream()
                .map(CounselResponseDto::new)
                .collect(Collectors.toList());
    }

    private Counsel findCounsel(Long counselId) {
        return counselRepository.findById(counselId)
                .orElseThrow(() -> new CounselNotFoundException(ErrorCode.COUNSEL_NOT_FOUND));
    }
}