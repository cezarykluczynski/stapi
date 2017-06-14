package com.cezarykluczynski.stapi.server.book_series.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBase;
import com.cezarykluczynski.stapi.client.v1.soap.BookSeriesBaseRequest;
import com.cezarykluczynski.stapi.model.book_series.dto.BookSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyHeaderSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {ComicsHeaderSoapMapper.class, BookSeriesHeaderSoapMapper.class, CompanyHeaderSoapMapper.class,
		RequestSortSoapMapper.class})
public interface BookSeriesBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(source = "publishedYear.from", target = "publishedYearFrom")
	@Mapping(source = "publishedYear.to", target = "publishedYearTo")
	@Mapping(source = "numberOfBooks.from", target = "numberOfBooksFrom")
	@Mapping(source = "numberOfBooks.to", target = "numberOfBooksTo")
	@Mapping(source = "year.from", target = "yearFrom")
	@Mapping(source = "year.to", target = "yearTo")
	BookSeriesRequestDTO mapBase(BookSeriesBaseRequest bookSeriesBaseRequest);

	BookSeriesBase mapBase(BookSeries bookSeries);

	List<BookSeriesBase> mapBase(List<BookSeries> bookSeriesList);

}
