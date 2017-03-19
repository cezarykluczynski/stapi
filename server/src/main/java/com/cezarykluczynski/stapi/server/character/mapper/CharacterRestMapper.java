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

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterSpeciesRestMapper.class, EnumMapper.class, EpisodeHeaderRestMapper.class,
		MovieHeaderRestMapper.class, PerformerHeaderRestMapper.class, RequestSortRestMapper.class})
public interface CharacterRestMapper {

	CharacterRequestDTO mapBase(CharacterRestBeanParams characterRestBeanParams);

	com.cezarykluczynski.stapi.client.v1.rest.model.CharacterBase mapBase(Character series);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.CharacterBase> mapBase(List<Character> series);

	com.cezarykluczynski.stapi.client.v1.rest.model.CharacterFull mapFull(Character series);

}
