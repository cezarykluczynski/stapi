package com.cezarykluczynski.stapi.model.configuration.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Converter(autoApply = true)
@Service
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Date> {

	@Override
	public Date convertToDatabaseColumn(LocalDateTime date) {
		return null == date ? null : Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
	}

	@Override
	public LocalDateTime convertToEntityAttribute(Date date) {
		return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}

}
