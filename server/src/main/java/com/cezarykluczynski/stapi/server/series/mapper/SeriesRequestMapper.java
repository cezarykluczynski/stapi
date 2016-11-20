package com.cezarykluczynski.stapi.server.series.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.SeriesRequest;
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestOrderMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, RequestOrderMapper.class})
public interface SeriesRequestMapper {

	@Mappings({
			@Mapping(source = "productionStartYear.from", target = "productionStartYearFrom"),
			@Mapping(source = "productionStartYear.to", target = "productionStartYearTo"),
			@Mapping(source = "productionEndYear.from", target = "productionEndYearFrom"),
			@Mapping(source = "productionEndYear.to", target = "productionEndYearTo"),
			@Mapping(source = "originalRunStartDate.dateFrom", target = "originalRunStartDateFrom"),
			@Mapping(source = "originalRunStartDate.dateTo", target = "originalRunStartDateTo"),
			@Mapping(source = "originalRunEndDate.dateFrom", target = "originalRunEndDateFrom"),
			@Mapping(source = "originalRunEndDate.dateTo", target = "originalRunEndDateTo")
	})
	SeriesRequestDTO map(SeriesRequest performerRequest);

	@Mappings({
			@Mapping(target = "order", ignore = true)
	})
	SeriesRequestDTO map(SeriesRestBeanParams performerRestBeanParams);

}
