package com.automobilepartnership.domain.counsel.service;

import com.automobilepartnership.api.dto.counsel.CounselRequestDto;
import com.automobilepartnership.common.converter.CounselTypeConverter;
import com.automobilepartnership.common.ErrorCode;
import com.automobilepartnership.domain.counsel.persistence.repository.ImageRepository;
import com.automobilepartnership.domain.counsel.service.exception.AlreadyAllocatedException;
import com.automobilepartnership.domain.counsel.service.exception.CounselNotFoundException;
import com.automobilepartnership.domain.counsel.dto.CounselResponseDto;
import com.automobilepartnership.domain.counsel.persistence.entity.Counsel;
import com.automobilepartnership.domain.counsel.persistence.repository.CounselRepository;
import com.automobilepartnership.domain.member.persistence.entity.Member;
import com.automobilepartnership.domain.member.persistence.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CounselService {

    private final CounselRepository counselRepository;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;

    @Transactional
    public Long create(Long userId, CounselRequestDto counselRequestDto) {
        Member member = memberRepository.getReferenceById(userId);
        CounselTypeConverter converter = new CounselTypeConverter();

        Counsel counsel = Counsel.builder()
                .member(member)
                .counselType(converter.convertToEntityAttribute(counselRequestDto.getType()))
                .title(counselRequestDto.getTitle())
                .detail(counselRequestDto.getDetail())
                .build();

        return counselRepository.save(counsel).getId();
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
        imageRepository.findByCounsel(counsel).forEach(imageRepository::delete); // 각 image 엔티티의 counsel 필드 삭제
        counselRepository.delete(counsel);
    }

    @Transactional(readOnly = true)
    public CounselResponseDto findCounselDto(Long counselId) {
        return new CounselResponseDto(findCounsel(counselId));
    }

    @Transactional(readOnly = true)
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