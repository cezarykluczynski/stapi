package com.cezarykluczynski.stapi.server.book_series.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesFull;
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsBaseRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyBaseRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {ComicsBaseRestMapper.class, BookSeriesBaseRestMapper.class, CompanyBaseRestMapper.class})
public interface BookSeriesFullRestMapper {

	BookSeriesFull mapFull(BookSeries bookSeries);

}
