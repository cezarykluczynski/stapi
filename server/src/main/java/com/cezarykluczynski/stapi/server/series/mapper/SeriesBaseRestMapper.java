package com.cezarykluczynski.stapi.server.series.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesBase;
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyHeaderRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyHeaderRestMapper.class, DateMapper.class, RequestSortRestMapper.class})
public interface SeriesBaseRestMapper {

	SeriesRequestDTO mapBase(SeriesRestBeanParams performerRestBeanParams);

	SeriesBase mapBase(Series series);

	List<SeriesBase> mapBase(List<Series> seriesList);

}
