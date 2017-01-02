package com.cezarykluczynski.stapi.server.movie.mapper;

import com.cezarykluczynski.stapi.client.v1.rest.model.MovieHeader;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.server.configuration.MapstructConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(config = MapstructConfiguration.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MovieHeaderRestMapper {

	MovieHeader map(Movie movie);

	List<MovieHeader> map(List<Movie> movie);

}
