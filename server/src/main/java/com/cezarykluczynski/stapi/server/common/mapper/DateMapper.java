package com.cezarykluczynski.stapi.server.common.mapper;

import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;

@Mapper(config = MapstructConfiguration.class)
public interface DateMapper {

	default LocalDate map(XMLGregorianCalendar xmlGregorianCalendar) {
		return xmlGregorianCalendar == null ? null : xmlGregorianCalendar.toGregorianCalendar().toZonedDateTime().toLocalDate();
	}

}
