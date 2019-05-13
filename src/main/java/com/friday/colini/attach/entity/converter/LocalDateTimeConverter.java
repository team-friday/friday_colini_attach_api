package com.friday.colini.attach.entity.converter;

import com.friday.colini.attach.utils.BeanUtils;
import com.friday.colini.attach.utils.DateConverter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.LocalDateTime;
import java.util.Date;

@Converter
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Date> {
    @Override
    public Date convertToDatabaseColumn(final LocalDateTime attribute) {
        return BeanUtils.getBean(DateConverter.class).localDateTimeToDate(attribute).orElse(new Date());
    }

    @Override
    public LocalDateTime convertToEntityAttribute(final Date dbData) {
        return BeanUtils.getBean(DateConverter.class).dateToLocalDateTime(dbData).orElse(LocalDateTime.now());
    }
}
