package com.cezarykluczynski.stapi.server.series.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullRequest;
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanySoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeHeaderSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyHeaderSoapMapper.class, CompanySoapMapper.class, DateMapper.class,
		EpisodeHeaderSoapMapper.class, RequestSortSoapMapper.class})
public interface SeriesSoapMapper {

	@Mappings({
			@Mapping(source = "productionStartYear.from", target = "productionStartYearFrom"),
			@Mapping(source = "productionStartYear.to", target = "productionStartYearTo"),
			@Mapping(source = "productionEndYear.from", target = "productionEndYearFrom"),
			@Mapping(source = "productionEndYear.to", target = "productionEndYearTo"),
			@Mapping(source = "originalRunStartDate.from", target = "originalRunStartDateFrom"),
			@Mapping(source = "originalRunStartDate.to", target = "originalRunStartDateTo"),
			@Mapping(source = "originalRunEndDate.from", target = "originalRunEndDateFrom"),
			@Mapping(source = "originalRunEndDate.to", target = "originalRunEndDateTo"),
			@Mapping(target = "guid", ignore = true)
	})
	SeriesRequestDTO mapBase(SeriesBaseRequest seriesBaseRequest);

	com.cezarykluczynski.stapi.client.v1.soap.SeriesBase mapBase(Series series);

	List<com.cezarykluczynski.stapi.client.v1.soap.SeriesBase> mapBase(List<Series> seriesList);

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
	com.cezarykluczynski.stapi.client.v1.soap.SeriesFull mapFull(Series seriesList);

}
