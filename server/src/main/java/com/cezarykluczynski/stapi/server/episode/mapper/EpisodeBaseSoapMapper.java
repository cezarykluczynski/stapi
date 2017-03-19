package com.cezarykluczynski.stapi.server.episode.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.EpisodeBaseRequest;
import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseSoapMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesSoapMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseSoapMapper.class, DateMapper.class, EnumMapper.class,
		RequestSortSoapMapper.class, PerformerBaseSoapMapper.class, SeriesHeaderSoapMapper.class, SeriesSoapMapper.class, StaffBaseSoapMapper.class})
public interface EpisodeBaseSoapMapper {

	@Mappings({
			@Mapping(target = "guid", ignore = true),
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
	EpisodeRequestDTO mapBase(EpisodeBaseRequest episodeBaseRequest);

	com.cezarykluczynski.stapi.client.v1.soap.EpisodeBase mapBase(Episode episode);

	List<com.cezarykluczynski.stapi.client.v1.soap.EpisodeBase> mapBase(List<Episode> episodeList);

}
