package com.cezarykluczynski.stapi.server.episode.mapper;

import com.cezarykluczynski.stapi.model.episode.dto.EpisodeRequestDTO;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.dto.EpisodeRestBeanParams;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerRestMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesHeaderRestMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesRestMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffRestMapper;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterRestMapper.class, DateMapper.class, EnumMapper.class, RequestSortRestMapper.class,
		PerformerRestMapper.class, SeriesHeaderRestMapper.class, SeriesRestMapper.class, StaffRestMapper.class})
public interface EpisodeRestMapper {

	EpisodeRequestDTO mapBase(EpisodeRestBeanParams performerRestBeanParams);

	com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeBase mapBase(Episode episode);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeBase> mapBase(List<Episode> episodeList);

	com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeFull mapFull(Episode episode);

}
