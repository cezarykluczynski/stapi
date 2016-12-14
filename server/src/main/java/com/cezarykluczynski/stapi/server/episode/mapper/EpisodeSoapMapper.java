package com.cezarykluczynski.stapi.server.episode.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeRequest;
import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestOrderMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffHeaderSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterHeaderSoapMapper.class, DateMapper.class,
		EnumMapper.class, RequestOrderMapper.class, PerformerHeaderSoapMapper.class, SeriesHeaderSoapMapper.class,
		StaffHeaderSoapMapper.class})
public interface EpisodeSoapMapper {

	@Mappings({
			@Mapping(source = "seasonNumber.from", target = "seasonNumberFrom"),
			@Mapping(source = "seasonNumber.to", target = "seasonNumberTo"),
			@Mapping(source = "episodeNumber.from", target = "episodeNumberFrom"),
			@Mapping(source = "episodeNumber.to", target = "episodeNumberTo"),
			@Mapping(source = "stardate.from", target = "stardateFrom"),
			@Mapping(source = "stardate.to", target = "stardateTo"),
			@Mapping(source = "year.from", target = "yearFrom"),
			@Mapping(source = "year.to", target = "yearTo"),
			@Mapping(source = "usAirDate.from", target = "usAirDateFrom"),
			@Mapping(source = "usAirDate.to", target = "usAirDateTo"),
			@Mapping(source = "finalScriptDate.from", target = "finalScriptDateFrom"),
			@Mapping(source = "finalScriptDate.to", target = "finalScriptDateTo")
	})
	EpisodeRequestDTO map(EpisodeRequest performerRequest);

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
	com.cezarykluczynski.stapi.client.v1.soap.Episode map(Episode episode);

	List<com.cezarykluczynski.stapi.client.v1.soap.Episode> map(List<Episode> episodeList);

}
