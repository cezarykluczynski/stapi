package com.cezarykluczynski.stapi.server.technology.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.TechnologyFull;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface TechnologyFullRestMapper {

	TechnologyFull mapFull(Technology technology);

}
