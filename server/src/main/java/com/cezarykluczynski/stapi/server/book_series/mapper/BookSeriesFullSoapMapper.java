package com.cezarykluczynski.stapi.server.book_series.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFull;
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesFullRequest;
import com.cezarykluczynski.stapi.model.book_series.dto.BookSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseSoapMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {ComicsBaseSoapMapper.class, BookSeriesBaseSoapMapper.class, CompanyBaseSoapMapper.class})
public interface BookSeriesFullSoapMapper {

	@Mapping(target = "title", ignore = true)
	@Mapping(target = "publishedYearFrom", ignore = true)
	@Mapping(target = "publishedYearTo", ignore = true)
	@Mapping(target = "numberOfBooksFrom", ignore = true)
	@Mapping(target = "numberOfBooksTo", ignore = true)
	@Mapping(target = "yearFrom", ignore = true)
	@Mapping(target = "yearTo", ignore = true)
	@Mapping(target = "miniseries", ignore = true)
	@Mapping(target = "EBookSeries", ignore = true)
	@Mapping(target = "sort", ignore = true)
	BookSeriesRequestDTO mapFull(BookSeriesFullRequest bookSeriesFullRequest);

	BookSeriesFull mapFull(BookSeries bookSeries);

}
