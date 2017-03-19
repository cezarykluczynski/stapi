package com.cezarykluczynski.stapi.server.episode.mapper;

import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseRestMapper;
import com.cezarykluczynski.stapi.server.series.mapper.SeriesRestMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseRestMapper.class, DateMapper.class, EnumMapper.class, RequestSortRestMapper.class,
		PerformerBaseRestMapper.class, SeriesRestMapper.class, StaffBaseRestMapper.class})
public interface EpisodeFullRestMapper {

	com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeFull mapFull(Episode episode);

}
