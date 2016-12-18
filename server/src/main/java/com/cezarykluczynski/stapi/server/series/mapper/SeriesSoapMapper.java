package com.cezarykluczynski.stapi.server.series.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.SeriesRequest;
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeHeaderSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EpisodeHeaderSoapMapper.class,
		RequestSortSoapMapper.class})
public interface SeriesSoapMapper {

	@Mappings({
			@Mapping(source = "productionStartYear.from", target = "productionStartYearFrom"),
			@Mapping(source = "productionStartYear.to", target = "productionStartYearTo"),
			@Mapping(source = "productionEndYear.from", target = "productionEndYearFrom"),
			@Mapping(source = "productionEndYear.to", target = "productionEndYearTo"),
			@Mapping(source = "originalRunStartDate.from", target = "originalRunStartDateFrom"),
			@Mapping(source = "originalRunStartDate.to", target = "originalRunStartDateTo"),
			@Mapping(source = "originalRunEndDate.from", target = "originalRunEndDateFrom"),
			@Mapping(source = "originalRunEndDate.to", target = "originalRunEndDateTo")
	})
	SeriesRequestDTO map(SeriesRequest performerRequest);

	@Mappings({
			@Mapping(source = "episodes", target = "episodeHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.soap.Series map(Series series);

	List<com.cezarykluczynski.stapi.client.v1.soap.Series> map(List<Series> seriesList);

}
