package com.cezarykluczynski.stapi.server.comic_series.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.ComicSeriesHeader;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ComicSeriesHeaderSoapMapper {

	ComicSeriesHeader map(ComicSeries comicSeries);

	List<ComicSeriesHeader> map(List<ComicSeries> comicSeries);

}
