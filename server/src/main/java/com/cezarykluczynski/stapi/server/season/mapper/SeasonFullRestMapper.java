package com.cezarykluczynski.stapi.server.season.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonFull;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class)
public interface SeasonFullRestMapper {

	SeasonFull mapFull(Season season);

}
