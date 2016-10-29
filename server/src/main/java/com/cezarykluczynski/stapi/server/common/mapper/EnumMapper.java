package com.cezarykluczynski.stapi.server.common.mapper;

import com.cezarykluczynski.stapi.client.soap.GenderEnum;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnumMapper {

	default GenderEnum mapFromEntityToSoapEnum(com.cezarykluczynski.stapi.model.common.entity.Gender gender) {
		return gender == null ? null : GenderEnum.valueOf(gender.name());
	}

}
