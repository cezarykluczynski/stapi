package com.cezarykluczynski.stapi.server.series.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.SeriesFull;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullRequest;
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyBaseSoapMapper.class, CompanyBaseSoapMapper.class, DateMapper.class,
		EpisodeBaseSoapMapper.class, RequestSortSoapMapper.class})
public interface SeriesFullSoapMapper {

	@Mappings({
			@Mapping(target = "title", ignore = true),
			@Mapping(target = "abbreviation", ignore = true),
			@Mapping(target = "productionStartYearFrom", ignore = true),
			@Mapping(target = "productionStartYearTo", ignore = true),
			@Mapping(target = "productionEndYearFrom", ignore = true),
			@Mapping(target = "productionEndYearTo", ignore = true),
			@Mapping(target = "originalRunStartDateFrom", ignore = true),
			@Mapping(target = "originalRunStartDateTo", ignore = true),
			@Mapping(target = "originalRunEndDateFrom", ignore = true),
			@Mapping(target = "originalRunEndDateTo", ignore = true),
			@Mapping(target = "sort", ignore = true)
	})
	SeriesRequestDTO mapFull(SeriesFullRequest seriesFullRequest);

	@Mappings({
			@Mapping(source = "episodes", target = "episodeHeaders")
	})
	SeriesFull mapFull(Series seriesList);
}
