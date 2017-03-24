package com.cezarykluczynski.stapi.server.species.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.SpeciesFull;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectRestMapper;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {AstronomicalObjectRestMapper.class, CharacterBaseRestMapper.class,
		RequestSortRestMapper.class})
public interface SpeciesFullRestMapper {

	SpeciesFull mapFull(Species series);

}
