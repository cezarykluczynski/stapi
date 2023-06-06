package com.cezarykluczynski.stapi.server.series.mapper;

import com.cezarykluczynski.stapi.client.rest.model.SeriesBase;
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyHeaderRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyHeaderRestMapper.class, DateMapper.class, RequestSortRestMapper.class})
public interface SeriesBaseRestMapper {

	@Mapping(target = "companionSeries", ignore = true)
	SeriesRequestDTO mapBase(SeriesRestBeanParams performerRestBeanParams);

	@AfterMapping
	default void mapBase(SeriesRestBeanParams seriesRestBeanParams, @MappingTarget SeriesRequestDTO seriesRequestDTO) {
		seriesRequestDTO.setCompanionSeries(false);
	}

	SeriesBase mapBase(Series series);

	List<SeriesBase> mapBase(List<Series> seriesList);

}
