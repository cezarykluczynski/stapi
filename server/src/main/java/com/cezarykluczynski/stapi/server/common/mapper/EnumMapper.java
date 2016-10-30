package com.cezarykluczynski.stapi.server.common.mapper;

import com.cezarykluczynski.stapi.client.soap.GenderEnum;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import com.cezarykluczynski.stapi.model.common.entity.Gender;

@Mapper(config = MapstructConfiguration.class)
public interface EnumMapper {

	default GenderEnum mapFromEntityEnumToSoapEnum(Gender gender) {
		return gender == null ? null : GenderEnum.valueOf(gender.name());
	}

	default Gender mapFromSoapEnumToEntityEnum(GenderEnum genderEnum) {
		return genderEnum == null ? null : com.cezarykluczynski.stapi.model.common.entity.Gender.valueOf(genderEnum.name());
	}

}
