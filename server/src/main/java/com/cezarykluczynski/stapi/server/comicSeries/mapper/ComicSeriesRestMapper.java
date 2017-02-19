package com.cezarykluczynski.stapi.server.comicSeries.mapper;

import com.cezarykluczynski.stapi.model.comicSeries.dto.ComicSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.server.comicSeries.dto.ComicSeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.comics.mapper.ComicsHeaderRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyHeaderRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {ComicsHeaderRestMapper.class, ComicSeriesHeaderRestMapper.class, CompanyHeaderRestMapper.class,
		RequestSortRestMapper.class})
public interface ComicSeriesRestMapper {

	ComicSeriesRequestDTO map(ComicSeriesRestBeanParams comicSeriesRestBeanParams);

	@Mappings({
			@Mapping(source = "parentSeries", target = "parentSeriesHeaders"),
			@Mapping(source = "childSeries", target = "childSeriesHeaders"),
			@Mapping(source = "publishers", target = "publisherHeaders"),
			@Mapping(source = "comics", target = "comicsHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeries map(ComicSeries comicSeries);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.ComicSeries> map(List<ComicSeries> comicSeriesList);

}
