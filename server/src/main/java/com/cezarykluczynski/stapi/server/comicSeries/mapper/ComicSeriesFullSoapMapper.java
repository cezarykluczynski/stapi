package com.cezarykluczynski.stapi.server.comicSeries.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFull;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesFullRequest;
import com.cezarykluczynski.stapi.model.comicSeries.dto.ComicSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanySoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = MapstructConfiguration.class, uses = {ComicsHeaderSoapMapper.class, ComicSeriesBaseSoapMapper.class, CompanySoapMapper.class})
public interface ComicSeriesFullSoapMapper {

	@Mappings({
			@Mapping(target = "title", ignore = true),
			@Mapping(target = "publishedYearFrom", ignore = true),
			@Mapping(target = "publishedYearTo", ignore = true),
			@Mapping(target = "numberOfIssuesFrom", ignore = true),
			@Mapping(target = "numberOfIssuesTo", ignore = true),
			@Mapping(target = "stardateFrom", ignore = true),
			@Mapping(target = "stardateTo", ignore = true),
			@Mapping(target = "yearFrom", ignore = true),
			@Mapping(target = "yearTo", ignore = true),
			@Mapping(target = "miniseries", ignore = true),
			@Mapping(target = "photonovelSeries", ignore = true),
			@Mapping(target = "sort", ignore = true)
	})
	ComicSeriesRequestDTO mapFull(ComicSeriesFullRequest comicSeriesFullRequest);

	ComicSeriesFull mapFull(ComicSeries comicSeries);

}
