package com.cezarykluczynski.stapi.server.movie.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.MovieBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieFullResponse;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.movie.dto.MovieRestBeanParams;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseRestMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieFullRestMapper;
import com.cezarykluczynski.stapi.server.movie.query.MovieRestQuery;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class MovieRestReader implements BaseReader<MovieRestBeanParams, MovieBaseResponse>, FullReader<String, MovieFullResponse> {

	private MovieRestQuery movieRestQuery;

	private MovieBaseRestMapper movieBaseRestMapper;

	private MovieFullRestMapper movieFullRestMapper;

	private PageMapper pageMapper;

	@Inject
	public MovieRestReader(MovieRestQuery movieRestQuery, MovieBaseRestMapper movieBaseRestMapper, MovieFullRestMapper movieFullRestMapper,
			PageMapper pageMapper) {
		this.movieRestQuery = movieRestQuery;
		this.movieBaseRestMapper = movieBaseRestMapper;
		this.movieFullRestMapper = movieFullRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public MovieBaseResponse readBase(MovieRestBeanParams movieRestBeanParams) {
		Page<Movie> moviePage = movieRestQuery.query(movieRestBeanParams);
		MovieBaseResponse movieResponse = new MovieBaseResponse();
		movieResponse.setPage(pageMapper.fromPageToRestResponsePage(moviePage));
		movieResponse.getMovies().addAll(movieBaseRestMapper.mapBase(moviePage.getContent()));
		return movieResponse;
	}

	@Override
	public MovieFullResponse readFull(String guid) {
		Preconditions.checkNotNull(guid, "GUID is required");
		MovieRestBeanParams movieRestBeanParams = new MovieRestBeanParams();
		movieRestBeanParams.setGuid(guid);
		Page<Movie> moviePage = movieRestQuery.query(movieRestBeanParams);
		MovieFullResponse movieResponse = new MovieFullResponse();
		movieResponse.setMovie(movieFullRestMapper.mapFull(Iterables.getOnlyElement(moviePage.getContent(), null)));
		return movieResponse;
	}

}
