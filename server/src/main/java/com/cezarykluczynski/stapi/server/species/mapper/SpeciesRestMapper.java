package com.cezarykluczynski.stapi.server.species.mapper;

import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectHeaderRestMapper;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterHeaderRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.species.dto.SpeciesRestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {AstronomicalObjectHeaderRestMapper.class, CharacterHeaderRestMapper.class,
		RequestSortRestMapper.class})
public interface SpeciesRestMapper {

	SpeciesRequestDTO map(SpeciesRestBeanParams speciesRestBeanParams);

	@Mappings({
			@Mapping(source = "characters", target = "characterHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.rest.model.Species map(Species series);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.Species> map(List<Species> series);

}
