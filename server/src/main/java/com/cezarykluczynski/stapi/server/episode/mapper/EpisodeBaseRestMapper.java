package com.cezarykluczynski.stapi.server.episode.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeBase;
import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.dto.EpisodeRestBeanParams;
import com.cezarykluczynski.stapi.server.season.mapper.SeasonHeaderRestMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesHeaderRestMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, RequestSortRestMapper.class, SeasonHeaderRestMapper.class,
		SeriesHeaderRestMapper.class})
public interface EpisodeBaseRestMapper {

	EpisodeRequestDTO mapBase(EpisodeRestBeanParams episodeRestBeanParams);

	EpisodeBase mapBase(Episode episode);

	List<EpisodeBase> mapBase(List<Episode> episodeList);

}
