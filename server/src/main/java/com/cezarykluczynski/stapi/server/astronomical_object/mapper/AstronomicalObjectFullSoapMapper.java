package com.cezarykluczynski.stapi.server.astronomical_object.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFull;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullRequest;
import com.cezarykluczynski.stapi.model.astronomical_object.dto.AstronomicalObjectRequestDTO;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {AstronomicalObjectBaseSoapMapper.class, EnumMapper.class})
public interface AstronomicalObjectFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "astronomicalObjectType", ignore = true)
	@Mapping(target = "locationUid", ignore = true)
	@Mapping(target = "sort", ignore = true)
	AstronomicalObjectRequestDTO mapFull(AstronomicalObjectFullRequest astronomicalObjectFullRequest);

	AstronomicalObjectFull mapFull(AstronomicalObject astronomicalObject);

}
