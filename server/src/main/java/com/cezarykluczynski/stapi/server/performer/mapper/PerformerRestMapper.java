package com.cezarykluczynski.stapi.server.performer.mapper;

import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterHeaderRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeHeaderRestMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieHeaderRestMapper;
import com.cezarykluczynski.stapi.server.performer.dto.PerformerRestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterHeaderRestMapper.class, DateMapper.class,
		EnumMapper.class, EpisodeHeaderRestMapper.class, MovieHeaderRestMapper.class, RequestSortRestMapper.class})
public interface PerformerRestMapper {

	PerformerRequestDTO map(PerformerRestBeanParams performerRestBeanParams);

	@Mappings({
			@Mapping(target = "episodesPerformanceHeaders", source = "episodesPerformances"),
			@Mapping(target = "episodesStuntPerformanceHeaders", source = "episodesStuntPerformances"),
			@Mapping(target = "episodesStandInPerformanceHeaders", source = "episodesStandInPerformances"),
			@Mapping(target = "moviesPerformanceHeaders", source = "moviesPerformances"),
			@Mapping(target = "moviesStuntPerformanceHeaders", source = "moviesStuntPerformances"),
			@Mapping(target = "moviesStandInPerformanceHeaders", source = "moviesStandInPerformances"),
			@Mapping(target = "characterHeaders", source = "characters")
	})
	com.cezarykluczynski.stapi.client.v1.rest.model.Performer map(Performer performer);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.Performer> map(List<Performer> performerList);

}
