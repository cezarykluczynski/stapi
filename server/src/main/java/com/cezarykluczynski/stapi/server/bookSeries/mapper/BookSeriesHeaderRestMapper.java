package com.cezarykluczynski.stapi.server.bookSeries.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.BookSeriesHeader;
import com.cezarykluczynski.stapi.model.bookSeries.entity.BookSeries;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookSeriesHeaderRestMapper {

	BookSeriesHeader map(BookSeries bookSeries);

	List<BookSeriesHeader> map(List<BookSeries> bookSeries);

}
