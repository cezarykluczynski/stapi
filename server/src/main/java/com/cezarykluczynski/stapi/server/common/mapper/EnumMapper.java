package com.cezarykluczynski.stapi.server.common.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.GenderEnum;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import com.cezarykluczynski.stapi.model.common.entity.Gender;

@Mapper(config = MapstructConfiguration.class)
public interface EnumMapper {

	default GenderEnum mapFromEntityEnumToSoapEnum(Gender gender) {
		return gender == null ? null : GenderEnum.valueOf(gender.name());
	}

	default com.cezarykluczynski.stapi.client.v1.rest.model.Gender mapFromEntityEnumToRestEnum(Gender gender) {
		return gender == null ? null : com.cezarykluczynski.stapi.client.v1.rest.model.Gender.valueOf(gender.name());
	}

	default Gender mapFromSoapEnumToEntityEnum(GenderEnum genderEnum) {
		return genderEnum == null ? null : Gender.valueOf(genderEnum.name());
	}

	default Gender mapFromRestEnumToEntityEnum(com.cezarykluczynski.stapi.client.v1.rest.model.Gender gender) {
		return gender == null ? null : Gender.valueOf(gender.name());
	}

}
