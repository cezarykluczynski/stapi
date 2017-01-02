package com.cezarykluczynski.stapi.server.movie.mapper;

import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.server.character.mapper.CharacterHeaderRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.movie.dto.MovieRestBeanParams;
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerHeaderRestMapper;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffHeaderRestMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {CharacterHeaderRestMapper.class, DateMapper.class,
		EnumMapper.class, RequestSortRestMapper.class, PerformerHeaderRestMapper.class, StaffHeaderRestMapper.class})
public interface MovieRestMapper {

	MovieRequestDTO map(MovieRestBeanParams movieRestBeanParams);

	@Mappings({
			@Mapping(source = "writers", target = "writerHeaders"),
			@Mapping(source = "screenplayAuthors", target = "screenplayAuthorHeaders"),
			@Mapping(source = "storyAuthors", target = "storyAuthorHeaders"),
			@Mapping(source = "directors", target = "directorHeaders"),
			@Mapping(source = "producers", target = "producerHeaders"),
			@Mapping(source = "performers", target = "performerHeaders"),
			@Mapping(source = "staff", target = "staffHeaders"),
			@Mapping(source = "stuntPerformers", target = "stuntPerformerHeaders"),
			@Mapping(source = "standInPerformers", target = "standInPerformerHeaders"),
			@Mapping(source = "characters", target = "characterHeaders")
	})
	com.cezarykluczynski.stapi.client.v1.rest.model.Movie map(Movie movie);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.Movie> map(List<Movie> movieList);

}
