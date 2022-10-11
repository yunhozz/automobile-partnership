package com.automobilepartnership.common.converter;

import com.automobilepartnership.domain.counsel.persistence.CounselType;

import javax.persistence.AttributeConverter;

public class CounselTypeConverter implements AttributeConverter<CounselType, String> {

    @Override
    public String convertToDatabaseColumn(CounselType attribute) {
        return attribute.getDesc();
    }

    @Override
    public CounselType convertToEntityAttribute(String dbData) {
        return CounselType.getCode(dbData);
    }
}