package com.cezarykluczynski.stapi.server.astronomicalObject.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectFull;
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {AstronomicalObjectBaseRestMapper.class, AstronomicalObjectHeaderRestMapper.class,
		EnumMapper.class})
public interface AstronomicalObjectFullRestMapper {

	AstronomicalObjectFull mapFull(AstronomicalObject astronomicalObject);

}
