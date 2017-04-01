package com.cezarykluczynski.stapi.server.performer.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.PerformerFull;
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullRequest;
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseSoapMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseSoapMapper.class, DateMapper.class, EnumMapper.class, EpisodeBaseSoapMapper.class,
		MovieBaseSoapMapper.class})
public interface PerformerFullSoapMapper {

	@Mapping(target = "name", ignore = true)
	@Mapping(target = "birthName", ignore = true)
	@Mapping(target = "gender", ignore = true)
	@Mapping(target = "dateOfBirthFrom", ignore = true)
	@Mapping(target = "dateOfBirthTo", ignore = true)
	@Mapping(target = "dateOfDeathFrom", ignore = true)
	@Mapping(target = "dateOfDeathTo", ignore = true)
	@Mapping(target = "placeOfBirth", ignore = true)
	@Mapping(target = "placeOfDeath", ignore = true)
	@Mapping(target = "sort", ignore = true)
	@Mapping(target = "animalPerformer", ignore = true)
	@Mapping(target = "disPerformer", ignore = true)
	@Mapping(target = "ds9Performer", ignore = true)
	@Mapping(target = "entPerformer", ignore = true)
	@Mapping(target = "filmPerformer", ignore = true)
	@Mapping(target = "standInPerformer", ignore = true)
	@Mapping(target = "stuntPerformer", ignore = true)
	@Mapping(target = "tasPerformer", ignore = true)
	@Mapping(target = "tngPerformer", ignore = true)
	@Mapping(target = "tosPerformer", ignore = true)
	@Mapping(target = "videoGamePerformer", ignore = true)
	@Mapping(target = "voicePerformer", ignore = true)
	@Mapping(target = "voyPerformer", ignore = true)
	PerformerRequestDTO mapFull(PerformerFullRequest performerFullRequest);

	PerformerFull mapFull(Performer performer);

}
