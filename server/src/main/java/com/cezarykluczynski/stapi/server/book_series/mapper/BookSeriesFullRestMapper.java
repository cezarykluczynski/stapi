package com.cezarykluczynski.stapi.server.book_series.mapper;

import com.cezarykluczynski.stapi.client.rest.model.BookSeriesFull;
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.server.book.mapper.BookBaseRestMapper;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {ComicsBaseRestMapper.class, BookSeriesBaseRestMapper.class, CompanyBaseRestMapper.class,
		BookBaseRestMapper.class})
public interface BookSeriesFullRestMapper {

	@Mapping(source = "EBookSeries", target = "eBookSeries")
	BookSeriesFull mapFull(BookSeries bookSeries);

}
