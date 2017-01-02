package com.cezarykluczynski.stapi.server.common.converter;

import javax.ws.rs.ext.ParamConverter;
import java.time.LocalDate;

public class LocalDateRestParamConverter implements ParamConverter<LocalDate> {

	@Override
	public LocalDate fromString(String value) {
		if (value == null) {
			return null;
		}

		String[] dateParts = value.split("-");

		if (dateParts.length != 3) {
			return null;
		}

		Integer year = Integer.valueOf(dateParts[0]);
		Integer month = Integer.valueOf(dateParts[1]);
		Integer day = Integer.valueOf(dateParts[2]);

		return LocalDate.of(year, month, day);
	}

	@Override
	public String toString(LocalDate value) {
		return null;
	}

}
