package com.cezarykluczynski.stapi.server.performer.mapper;

import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterHeaderRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseRestMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieHeaderRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterHeaderRestMapper.class, CharacterBaseRestMapper.class, DateMapper.class,
		EnumMapper.class, EpisodeBaseRestMapper.class, MovieHeaderRestMapper.class})
public interface PerformerFullRestMapper {

	com.cezarykluczynski.stapi.client.v1.rest.model.PerformerFull mapFull(Performer performer);

}
