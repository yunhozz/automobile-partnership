package com.automobilepartnership.domain.counsel.persistence;

import com.automobilepartnership.api.dto.counsel.SortType;
import com.automobilepartnership.common.converter.SortTypeConverter;
import com.automobilepartnership.domain.counsel.dto.CounselQueryDto;
import com.automobilepartnership.domain.counsel.dto.QCounselQueryDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.automobilepartnership.domain.counsel.persistence.QCounsel.*;
import static com.automobilepartnership.domain.member.persistence.QMember.*;

@Repository
@RequiredArgsConstructor
public class CounselCustomRepositoryImpl implements CounselCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<CounselQueryDto> sortPage(String sortTypeDesc, Pageable pageable) {
        SortTypeConverter converter = new SortTypeConverter();
        SortType sortType = converter.convertToEntityAttribute(sortTypeDesc);

        List<CounselQueryDto> counselList = queryFactory
                .select(new QCounselQueryDto(
                        counsel.id,
                        counsel.counselType.stringValue(),
                        counsel.title,
                        counsel.isResolved,
                        counsel.createdDate,
                        member.id,
                        member.baseInfo.name
                ))
                .from(counsel)
                .join(counsel.member, member)
                .where(bySortType(sortType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(counsel.createdDate.desc())
                .fetch();

        Long count = queryFactory
                .select(counsel.count())
                .from(counsel)
                .fetchOne();

        return new PageImpl<>(counselList, pageable, count);
    }

    @Override
    public Page<CounselQueryDto> searchPageWithKeyword(String keyword, Pageable pageable) {
        List<CounselQueryDto> counselList = queryFactory
                .select(new QCounselQueryDto(
                        counsel.id,
                        counsel.counselType.stringValue(),
                        counsel.title,
                        counsel.isResolved,
                        counsel.createdDate,
                        member.id,
                        member.baseInfo.name
                ))
                .from(counsel)
                .join(counsel.member, member)
                .where(byKeyword(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(counsel.createdDate.desc())
                .fetch();

        Long count = queryFactory
                .select(counsel.count())
                .from(counsel)
                .fetchOne();

        return new PageImpl<>(counselList, pageable, count);
    }

    private BooleanExpression bySortType(SortType sortType) {
        switch (sortType) {
            case RESOLVED:
                return counsel.isResolved.isTrue();

            case NOT_RESOLVED:
                return counsel.isResolved.isFalse();

            default:
                return null;
        }
    }

    private BooleanExpression byKeyword(String keyword) {
        return StringUtils.hasText(keyword) ? counsel.title.contains(keyword).or(counsel.detail.contains(keyword)) : null;
    }
}