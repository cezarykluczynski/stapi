package com.cezarykluczynski.stapi.server.staff.mapper;

import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortRestMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeHeaderRestMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieHeaderRestMapper;
import com.cezarykluczynski.stapi.server.staff.dto.StaffRestBeanParams;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class,
		EpisodeHeaderRestMapper.class, MovieHeaderRestMapper.class, RequestSortRestMapper.class})
public interface StaffRestMapper {

	StaffRequestDTO map(StaffRestBeanParams performerRestBeanParams);

	@Mappings({
			@Mapping(target = "writtenEpisodeHeaders", source = "writtenEpisodes"),
			@Mapping(target = "teleplayAuthoredEpisodeHeaders", source = "teleplayAuthoredEpisodes"),
			@Mapping(target = "storyAuthoredEpisodeHeaders", source = "storyAuthoredEpisodes"),
			@Mapping(target = "directedEpisodeHeaders", source = "directedEpisodes"),
			@Mapping(target = "episodeHeaders", source = "episodes"),
			@Mapping(target = "writtenMovieHeaders", source = "writtenMovies"),
			@Mapping(target = "screenplayAuthoredMovieHeaders", source = "screenplayAuthoredMovies"),
			@Mapping(target = "storyAuthoredMovieHeaders", source = "storyAuthoredMovies"),
			@Mapping(target = "directedMovieHeaders", source = "directedMovies"),
			@Mapping(target = "producedMovieHeaders", source = "producedMovies"),
			@Mapping(target = "movieHeaders", source = "movies")
	})
	com.cezarykluczynski.stapi.client.v1.rest.model.Staff map(Staff staff);

	List<com.cezarykluczynski.stapi.client.v1.rest.model.Staff> map(List<Staff> staffList);

}
