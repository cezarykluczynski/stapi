package com.cezarykluczynski.stapi.server.character.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.CharacterFull;
import com.cezarykluczynski.stapi.client.v1.soap.CharacterFullRequest;
import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseSoapMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseSoapMapper;
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseSoapMapper;
import com.cezarykluczynski.stapi.server.organization.mapper.OrganizationBaseSoapMapper;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseSoapMapper;
import com.cezarykluczynski.stapi.server.title.mapper.TitleBaseSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterRelationSoapMapper.class, CharacterSpeciesSoapMapper.class, EnumMapper.class,
		EpisodeBaseSoapMapper.class, MovieBaseSoapMapper.class, OccupationBaseSoapMapper.class, OrganizationBaseSoapMapper.class,
		PerformerBaseSoapMapper.class, TitleBaseSoapMapper.class})
public interface CharacterFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "gender", ignore = true)
	@Mapping(target = "deceased", ignore = true)
	@Mapping(target = "hologram", ignore = true)
	@Mapping(target = "fictionalCharacter", ignore = true)
	@Mapping(target = "mirror", ignore = true)
	@Mapping(target = "alternateReality", ignore = true)
	@Mapping(target = "sort", ignore = true)
	CharacterRequestDTO mapFull(CharacterFullRequest characterFullRequest);

	CharacterFull mapFull(Character character);

}
