package com.cezarykluczynski.stapi.server.performer.mapper;

import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterHeaderRestMapper;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeHeaderRestMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieHeaderRestMapper;
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterHeaderRestMapper.class, CharacterRestMapper.class, DateMapper.class,
		EnumMapper.class, EpisodeHeaderRestMapper.class, MovieHeaderRestMapper.class, RequestSortRestMapper.class})
public interface PerformerRestMapper {

	PerformerRequestDTO mapBase(PerformerRestBeanParams performerRestBeanParams);

	com.cezarykluczynski.stapi.client.v1.rest.model.PerformerBase mapBase(Performer performer);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.PerformerBase> mapBase(List<Performer> performerList);

	com.cezarykluczynski.stapi.client.v1.rest.model.PerformerFull mapFull(Performer performer);
}
