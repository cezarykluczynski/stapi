package com.cezarykluczynski.stapi.server.book_series.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesBase;
import com.cezarykluczynski.stapi.model.book_series.dto.BookSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.server.book_series.dto.BookSeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {RequestSortRestMapper.class})
public interface BookSeriesBaseRestMapper {

	BookSeriesRequestDTO mapBase(BookSeriesRestBeanParams bookSeriesRestBeanParams);

	BookSeriesBase mapBase(BookSeries bookSeries);

	List<BookSeriesBase> mapBase(List<BookSeries> bookSeriesList);

}
