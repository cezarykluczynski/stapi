package com.cezarykluczynski.stapi.server.comic_series.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBase;
import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesBaseRequest;
import com.cezarykluczynski.stapi.model.comic_series.dto.ComicSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {ComicsHeaderSoapMapper.class, ComicSeriesHeaderSoapMapper.class, CompanyHeaderSoapMapper.class,
		RequestSortSoapMapper.class})
public interface ComicSeriesBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(source = "publishedYear.from", target = "publishedYearFrom")
	@Mapping(source = "publishedYear.to", target = "publishedYearTo")
	@Mapping(source = "numberOfIssues.from", target = "numberOfIssuesFrom")
	@Mapping(source = "numberOfIssues.to", target = "numberOfIssuesTo")
	@Mapping(source = "stardate.from", target = "stardateFrom")
	@Mapping(source = "stardate.to", target = "stardateTo")
	@Mapping(source = "year.from", target = "yearFrom")
	@Mapping(source = "year.to", target = "yearTo")
	ComicSeriesRequestDTO mapBase(ComicSeriesBaseRequest comicSeriesBaseRequest);

	ComicSeriesBase mapBase(ComicSeries comicSeries);

	List<ComicSeriesBase> mapBase(List<ComicSeries> comicSeriesList);

}
