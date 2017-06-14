package com.cezarykluczynski.stapi.server.comic_series.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesFull;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {ComicsBaseRestMapper.class, ComicSeriesBaseRestMapper.class, CompanyBaseRestMapper.class})
public interface ComicSeriesFullRestMapper {

	ComicSeriesFull mapFull(ComicSeries comicSeries);

}
