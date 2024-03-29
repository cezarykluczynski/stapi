package com.cezarykluczynski.stapi.server.staff.mapper;

import com.cezarykluczynski.stapi.client.rest.model.StaffFull;
import com.cezarykluczynski.stapi.client.rest.model.StaffV2Full;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseRestMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseRestMapper;
import org.mapstruct.Mapper;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, EpisodeBaseRestMapper.class, MovieBaseRestMapper.class})
public interface StaffFullRestMapper {

	StaffFull mapFull(Staff staff);

	StaffV2Full mapV2Full(Staff staff);

}
