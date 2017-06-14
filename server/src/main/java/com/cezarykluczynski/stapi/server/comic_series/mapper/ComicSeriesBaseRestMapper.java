package com.cezarykluczynski.stapi.server.comic_series.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeriesBase;
import com.cezarykluczynski.stapi.model.comic_series.dto.ComicSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.server.comic_series.dto.ComicSeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface ComicSeriesBaseRestMapper {

	ComicSeriesRequestDTO mapBase(ComicSeriesRestBeanParams comicSeriesRestBeanParams);

	ComicSeriesBase mapBase(ComicSeries comicSeries);

	List<ComicSeriesBase> mapBase(List<ComicSeries> comicSeriesList);

}
