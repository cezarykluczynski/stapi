package com.cezarykluczynski.stapi.server.episode.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFull;
import com.cezarykluczynski.stapi.client.v1.soap.EpisodeFullRequest;
import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseSoapMapper;
import com.cezarykluczynski.stapi.server.season.mapper.SeasonBaseSoapMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseSoapMapper.class, DateMapper.class, EnumMapper.class, RequestSortSoapMapper.class,
		PerformerBaseSoapMapper.class, SeasonBaseSoapMapper.class, SeriesBaseSoapMapper.class, StaffBaseSoapMapper.class})
public interface EpisodeFullSoapMapper {

	@Mapping(target = "title", ignore = true)
	@Mapping(target = "productionSerialNumber", ignore = true)
	@Mapping(target = "featureLength", ignore = true)
	@Mapping(target = "seasonNumberFrom", ignore = true)
	@Mapping(target = "seasonNumberTo", ignore = true)
	@Mapping(target = "episodeNumberFrom", ignore = true)
	@Mapping(target = "episodeNumberTo", ignore = true)
	@Mapping(target = "stardateFrom", ignore = true)
	@Mapping(target = "stardateTo", ignore = true)
	@Mapping(target = "yearFrom", ignore = true)
	@Mapping(target = "yearTo", ignore = true)
	@Mapping(target = "usAirDateFrom", ignore = true)
	@Mapping(target = "usAirDateTo", ignore = true)
	@Mapping(target = "finalScriptDateFrom", ignore = true)
	@Mapping(target = "finalScriptDateTo", ignore = true)
	@Mapping(target = "sort", ignore = true)
	EpisodeRequestDTO mapFull(EpisodeFullRequest episodeFullRequest);

	EpisodeFull mapFull(Episode episode);

}
