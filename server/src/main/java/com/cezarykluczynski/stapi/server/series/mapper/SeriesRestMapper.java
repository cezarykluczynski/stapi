package com.cezarykluczynski.stapi.server.series.mapper;

import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class)
public interface SeriesRestMapper {

	com.cezarykluczynski.stapi.client.v1.rest.model.Series map(Series series);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.Series> map(List<Series> seriesList);

}
