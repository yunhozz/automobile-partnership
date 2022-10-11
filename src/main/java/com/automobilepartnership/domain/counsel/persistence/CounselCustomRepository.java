package com.automobilepartnership.domain.counsel.persistence;

import com.automobilepartnership.domain.counsel.dto.CounselQueryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CounselCustomRepository {

    Page<CounselQueryDto> sortPage(String sortTypeDesc, Pageable pageable);
    Page<CounselQueryDto> searchPageWithKeyword(String keyword, Pageable pageable);
}