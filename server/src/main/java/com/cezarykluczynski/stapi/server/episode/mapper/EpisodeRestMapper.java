package com.cezarykluczynski.stapi.server.episode.mapper;

import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterHeaderRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.dto.EpisodeRestBeanParams;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerHeaderRestMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesHeaderRestMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffHeaderRestMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterHeaderRestMapper.class, DateMapper.class,
		EnumMapper.class, RequestSortRestMapper.class, PerformerHeaderRestMapper.class, SeriesHeaderRestMapper.class,
		StaffHeaderRestMapper.class})
public interface EpisodeRestMapper {

	EpisodeRequestDTO map(EpisodeRestBeanParams performerRestBeanParams);

	@Mappings({
			@Mapping(source = "writers", target = "writerHeaders"),
			@Mapping(source = "teleplayAuthors", target = "teleplayAuthorHeaders"),
			@Mapping(source = "storyAuthors", target = "storyAuthorHeaders"),
			@Mapping(source = "directors", target = "directorHeaders"),
			@Mapping(source = "performers", target = "performerHeaders"),
			@Mapping(source = "stuntPerformers", target = "stuntPerformerHeaders"),
			@Mapping(source = "standInPerformers", target = "standInPerformerHeaders"),
			@Mapping(source = "characters", target = "characterHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.rest.model.Episode map(Episode episode);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.Episode> map(List<Episode> episodeList);

}
