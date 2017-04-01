package com.cezarykluczynski.stapi.server.movie.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.MovieFull;
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullRequest;
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterBaseSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseSoapMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffBaseSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterBaseSoapMapper.class, DateMapper.class, EnumMapper.class,
		PerformerBaseSoapMapper.class, StaffBaseSoapMapper.class})
public interface MovieFullSoapMapper {

	@Mapping(target = "title", ignore = true)
	@Mapping(target = "stardateFrom", ignore = true)
	@Mapping(target = "stardateTo", ignore = true)
	@Mapping(target = "yearFrom", ignore = true)
	@Mapping(target = "yearTo", ignore = true)
	@Mapping(target = "usReleaseDateFrom", ignore = true)
	@Mapping(target = "usReleaseDateTo", ignore = true)
	@Mapping(target = "sort", ignore = true)
	MovieRequestDTO mapFull(MovieFullRequest movieFullRequest);

	MovieFull mapFull(Movie movie);

}
