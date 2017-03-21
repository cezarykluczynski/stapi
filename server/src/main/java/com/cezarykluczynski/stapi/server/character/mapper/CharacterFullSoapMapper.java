package com.cezarykluczynski.stapi.server.character.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.CharacterFull;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullRequest;
import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseSoapMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterSpeciesSoapMapper.class, EnumMapper.class, EpisodeBaseSoapMapper.class,
		MovieHeaderSoapMapper.class, PerformerBaseSoapMapper.class})
public interface CharacterFullSoapMapper {

	@Mappings({
			@Mapping(target = "name", ignore = true),
			@Mapping(target = "gender", ignore = true),
			@Mapping(target = "deceased", ignore = true),
			@Mapping(target = "mirror", ignore = true),
			@Mapping(target = "alternateReality", ignore = true),
			@Mapping(target = "sort", ignore = true)
	})
	CharacterRequestDTO mapFull(CharacterFullRequest characterFullRequest);

	CharacterFull mapFull(Character character);

}
