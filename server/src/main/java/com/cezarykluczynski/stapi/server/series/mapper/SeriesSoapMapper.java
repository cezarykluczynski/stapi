package com.cezarykluczynski.stapi.server.series.mapper;

import com.cezarykluczynski.stapi.model.series.entity.Series;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeriesSoapMapper {

	com.cezarykluczynski.stapi.client.soap.Series map(Series series);

	List<com.cezarykluczynski.stapi.client.soap.Series> map(List<Series> seriesList);

}
