package com.cezarykluczynski.stapi.server.comicSeries.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesFull;
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsHeaderRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {ComicsHeaderRestMapper.class, ComicSeriesBaseRestMapper.class, CompanyRestMapper.class})
public interface ComicSeriesFullRestMapper {

	ComicSeriesFull mapFull(ComicSeries comicSeries);

}
