package com.cezarykluczynski.stapi.server.movie.mapper;

import com.cezarykluczynski.stapi.client.v1.soap.MovieBase;
import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseRequest;
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.server.common.mapper.DateMapper;
import com.cezarykluczynski.stapi.server.common.mapper.EnumMapper;
import com.cezarykluczynski.stapi.server.common.mapper.RequestSortSoapMapper;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import com.cezarykluczynski.stapi.server.staff.mapper.StaffHeaderSoapMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, uses = {DateMapper.class, EnumMapper.class, RequestSortSoapMapper.class, StaffHeaderSoapMapper.class})
public interface MovieBaseSoapMapper {

	@Mapping(target = "uid", ignore = true)
	@Mapping(source = "stardate.from", target = "stardateFrom")
	@Mapping(source = "stardate.to", target = "stardateTo")
	@Mapping(source = "year.from", target = "yearFrom")
	@Mapping(source = "year.to", target = "yearTo")
	@Mapping(source = "usReleaseDate.from", target = "usReleaseDateFrom")
	@Mapping(source = "usReleaseDate.to", target = "usReleaseDateTo")
	MovieRequestDTO mapBase(MovieBaseRequest movieBaseRequest);

	MovieBase mapBase(Movie movie);

	List<MovieBase> mapBase(List<Movie> movieList);

}
