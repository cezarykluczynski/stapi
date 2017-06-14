package com.cezarykluczynski.stapi.server.comic_series.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFull;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullRequest;
import com.cezarykluczynski.stapi.model.comic_series.dto.ComicSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseSoapMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {ComicsBaseSoapMapper.class, ComicSeriesBaseSoapMapper.class, CompanyBaseSoapMapper.class})
public interface ComicSeriesFullSoapMapper {

	@Mapping(target = "title", ignore = true)
	@Mapping(target = "publishedYearFrom", ignore = true)
	@Mapping(target = "publishedYearTo", ignore = true)
	@Mapping(target = "numberOfIssuesFrom", ignore = true)
	@Mapping(target = "numberOfIssuesTo", ignore = true)
	@Mapping(target = "stardateFrom", ignore = true)
	@Mapping(target = "stardateTo", ignore = true)
	@Mapping(target = "yearFrom", ignore = true)
	@Mapping(target = "yearTo", ignore = true)
	@Mapping(target = "miniseries", ignore = true)
	@Mapping(target = "photonovelSeries", ignore = true)
	@Mapping(target = "sort", ignore = true)
	ComicSeriesRequestDTO mapFull(ComicSeriesFullRequest comicSeriesFullRequest);

	ComicSeriesFull mapFull(ComicSeries comicSeries);

}
