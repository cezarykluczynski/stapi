package com.cezarykluczynski.stapi.server.staff.mapper;

import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseRestMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, EpisodeBaseRestMapper.class, MovieBaseRestMapper.class})
public interface StaffFullRestMapper {

	com.cezarykluczynski.stapi.client.v1.rest.model.StaffFull mapFull(Staff staff);

}
