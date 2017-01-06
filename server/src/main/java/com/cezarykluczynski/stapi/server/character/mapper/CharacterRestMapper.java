package com.cezarykluczynski.stapi.server.character.mapper;

import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.server.character.dto.CharacterRestBeanParams;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeHeaderRestMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieHeaderRestMapper;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerHeaderRestMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {EnumMapper.class, EpisodeHeaderRestMapper.class,
		MovieHeaderRestMapper.class, PerformerHeaderRestMapper.class, RequestSortRestMapper.class})
public interface CharacterRestMapper {

	CharacterRequestDTO map(CharacterRestBeanParams characterRestBeanParams);

	@Mappings({
			@Mapping(source = "performers", target = "performerHeaders"),
			@Mapping(source = "episodes", target = "episodeHeaders"),
			@Mapping(source = "movies", target = "movieHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.rest.model.Character map(Character series);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.Character> map(List<Character> series);

}
