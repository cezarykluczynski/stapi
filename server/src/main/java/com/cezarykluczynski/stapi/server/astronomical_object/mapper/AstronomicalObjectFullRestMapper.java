package com.cezarykluczynski.stapi.server.astronomical_object.mapper;

import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectFull;
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2Full;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {AstronomicalObjectBaseRestMapper.class, EnumMapper.class})
public interface AstronomicalObjectFullRestMapper {

	AstronomicalObjectFull mapFull(AstronomicalObject astronomicalObject);

	AstronomicalObjectV2Full mapV2Full(AstronomicalObject astronomicalObject);

}
