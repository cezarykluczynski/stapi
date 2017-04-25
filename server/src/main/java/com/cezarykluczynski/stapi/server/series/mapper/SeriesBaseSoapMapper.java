package com.cezarykluczynski.stapi.server.series.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.SeriesBase;
import com.cezarykluczynski.stapi.client.v1.soap.SeriesBaseRequest;
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseSoapMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeHeaderSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyHeaderSoapMapper.class, CompanyBaseSoapMapper.class, DateMapper.class,
		EpisodeHeaderSoapMapper.class, RequestSortSoapMapper.class})
public interface SeriesBaseSoapMapper {

	@Mapping(source = "productionStartYear.from", target = "productionStartYearFrom")
	@Mapping(source = "productionStartYear.to", target = "productionStartYearTo")
	@Mapping(source = "productionEndYear.from", target = "productionEndYearFrom")
	@Mapping(source = "productionEndYear.to", target = "productionEndYearTo")
	@Mapping(source = "originalRunStartDate.from", target = "originalRunStartDateFrom")
	@Mapping(source = "originalRunStartDate.to", target = "originalRunStartDateTo")
	@Mapping(source = "originalRunEndDate.from", target = "originalRunEndDateFrom")
	@Mapping(source = "originalRunEndDate.to", target = "originalRunEndDateTo")
	@Mapping(target = "uid", ignore = true)
	SeriesRequestDTO mapBase(SeriesBaseRequest seriesBaseRequest);

	SeriesBase mapBase(Series series);

	List<SeriesBase> mapBase(List<Series> seriesList);

}
