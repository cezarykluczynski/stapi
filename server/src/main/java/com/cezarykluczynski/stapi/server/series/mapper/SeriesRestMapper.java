package com.cezarykluczynski.stapi.server.series.mapper;

import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestOrderMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, RequestOrderMapper.class})
public interface SeriesRestMapper {

	@Mappings({
			@Mapping(target = "order", ignore = true)
	})
	SeriesRequestDTO map(SeriesRestBeanParams performerRestBeanParams);

	com.cezarykluczynski.stapi.client.v1.rest.model.Series map(Series series);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.Series> map(List<Series> seriesList);

}
