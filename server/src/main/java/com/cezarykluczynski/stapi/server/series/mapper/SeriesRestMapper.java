package com.cezarykluczynski.stapi.server.series.mapper;

import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, RequestSortRestMapper.class})
public interface SeriesRestMapper {

	SeriesRequestDTO map(SeriesRestBeanParams performerRestBeanParams);

	com.cezarykluczynski.stapi.client.v1.rest.model.Series map(Series series);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.Series> map(List<Series> seriesList);

}
