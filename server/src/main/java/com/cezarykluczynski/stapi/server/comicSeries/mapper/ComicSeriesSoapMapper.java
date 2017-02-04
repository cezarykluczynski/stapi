package com.cezarykluczynski.stapi.server.comicSeries.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesRequest;
import com.cezarykluczynski.stapi.model.comicSeries.dto.ComicSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {ComicSeriesHeaderSoapMapper.class, RequestSortSoapMapper.class})
public interface ComicSeriesSoapMapper {

	@Mappings({
			@Mapping(source = "publishedYear.from", target = "publishedYearFrom"),
			@Mapping(source = "publishedYear.to", target = "publishedYearTo"),
			@Mapping(source = "numberOfIssues.from", target = "numberOfIssuesFrom"),
			@Mapping(source = "numberOfIssues.to", target = "numberOfIssuesTo"),
			@Mapping(source = "stardate.from", target = "stardateFrom"),
			@Mapping(source = "stardate.to", target = "stardateTo"),
			@Mapping(source = "year.from", target = "yearFrom"),
			@Mapping(source = "year.to", target = "yearTo")
	})
	ComicSeriesRequestDTO map(ComicSeriesRequest comicSeriesRequest);

	@Mappings({
			@Mapping(source = "parentSeries", target = "parentSeriesHeaders"),
			@Mapping(source = "childSeries", target = "childSeriesHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.soap.ComicSeries map(ComicSeries comicSeries);

	List<com.cezarykluczynski.stapi.client.v1.soap.ComicSeries> map(List<ComicSeries> comicSeriesList);

}
