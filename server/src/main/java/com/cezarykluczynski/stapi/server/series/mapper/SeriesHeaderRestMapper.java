package com.cezarykluczynski.stapi.server.series.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.SeriesHeader;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SeriesHeaderRestMapper {

	SeriesHeader map(Series series);

	List<SeriesHeader> map(List<Series> seriesList);

}
