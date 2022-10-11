package com.automobilepartnership.common.converter;

import com.automobilepartnership.api.dto.counsel.SortType;

import javax.persistence.AttributeConverter;

public class SortTypeConverter implements AttributeConverter<SortType, String> {

    @Override
    public String convertToDatabaseColumn(SortType attribute) {
        return attribute.getDesc();
    }

    @Override
    public SortType convertToEntityAttribute(String dbData) {
        return SortType.getCode(dbData);
    }
}