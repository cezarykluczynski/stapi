package com.cezarykluczynski.stapi.etl.template.planet.mapper;

import com.cezarykluczynski.stapi.model.astronomical_object.entity.enums.AstronomicalObjectType;
import org.springframework.stereotype.Service;

@Service
public class AstronomicalObjectTypeMapper {

	public AstronomicalObjectType fromEtlToModel(com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType
			astronomicalObjectType) {
		return astronomicalObjectType == null ? null : AstronomicalObjectType.valueOf(astronomicalObjectType.name());
	}

}
