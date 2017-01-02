package com.cezarykluczynski.stapi.server.common.converter;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.LocalDate;

public class LocalDateRestParamConverterProvider implements ParamConverterProvider {

	private ParamConverter<LocalDate> localDateParamConverter = new LocalDateRestParamConverter();

	@Override
	public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
		if (rawType.equals(LocalDate.class)) {
			return (ParamConverter<T>) localDateParamConverter;
		}

		return null;
	}
}
