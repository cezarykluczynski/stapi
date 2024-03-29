package com.cezarykluczynski.stapi.server.occupation.mapper;

import com.cezarykluczynski.stapi.client.rest.model.OccupationFull;
import com.cezarykluczynski.stapi.client.rest.model.OccupationV2Full;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseRestMapper.class})
public interface OccupationFullRestMapper {

	OccupationFull mapFull(Occupation occupation);

	OccupationV2Full mapV2Full(Occupation occupation);

}
