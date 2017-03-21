package com.cezarykluczynski.stapi.server.character.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.CharacterFull;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseRestMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieHeaderRestMapper;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterSpeciesRestMapper.class, EnumMapper.class, EpisodeBaseRestMapper.class,
		MovieHeaderRestMapper.class, PerformerBaseRestMapper.class})
public interface CharacterFullRestMapper {

	CharacterFull mapFull(Character series);

}
