package com.cezarykluczynski.stapi.server.series.mapper;

import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyHeaderRestMapper;
import com.cezarykluczynski.stapi.server.company.mapper.CompanyRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeHeaderRestMapper;
import com.cezarykluczynski.stapi.server.series.dto.SeriesRestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CompanyHeaderRestMapper.class, CompanyRestMapper.class, DateMapper.class,
		EpisodeHeaderRestMapper.class, RequestSortRestMapper.class})
public interface SeriesRestMapper {

	SeriesRequestDTO mapBase(SeriesRestBeanParams performerRestBeanParams);

	com.cezarykluczynski.stapi.client.v1.rest.model.SeriesBase mapBase(Series series);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.SeriesBase> mapBase(List<Series> seriesList);

	@Mappings({
			@Mapping(source = "episodes", target = "episodeHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.rest.model.SeriesFull mapFull(Series series);

}
