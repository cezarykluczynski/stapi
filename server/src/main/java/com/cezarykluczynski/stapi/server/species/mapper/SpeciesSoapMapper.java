package com.cezarykluczynski.stapi.server.species.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.SpeciesRequest;
import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {AstronomicalObjectHeaderSoapMapper.class, CharacterHeaderSoapMapper.class,
		RequestSortSoapMapper.class})
public interface SpeciesSoapMapper {

	SpeciesRequestDTO map(SpeciesRequest speciesRequest);

	@Mappings({
			@Mapping(source = "characters", target = "characterHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.soap.Species map(Species species);

	List<com.cezarykluczynski.stapi.client.v1.soap.Species> map(List<Species> speciesList);

}
