package com.cezarykluczynski.stapi.server.movie.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.MovieBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieFullResponse;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.movie.dto.MovieRestBeanParams;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseRestMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieFullRestMapper;
import com.cezarykluczynski.stapi.server.movie.query.MovieRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class MovieRestReader implements BaseReader<MovieRestBeanParams, MovieBaseResponse>, FullReader<String, MovieFullResponse> {

	private final MovieRestQuery movieRestQuery;

	private final MovieBaseRestMapper movieBaseRestMapper;

	private final MovieFullRestMapper movieFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public MovieRestReader(MovieRestQuery movieRestQuery, MovieBaseRestMapper movieBaseRestMapper, MovieFullRestMapper movieFullRestMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.movieRestQuery = movieRestQuery;
		this.movieBaseRestMapper = movieBaseRestMapper;
		this.movieFullRestMapper = movieFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public MovieBaseResponse readBase(MovieRestBeanParams input) {
		Page<Movie> moviePage = movieRestQuery.query(input);
		MovieBaseResponse movieResponse = new MovieBaseResponse();
		movieResponse.setPage(pageMapper.fromPageToRestResponsePage(moviePage));
		movieResponse.setSort(sortMapper.map(input.getSort()));
		movieResponse.getMovies().addAll(movieBaseRestMapper.mapBase(moviePage.getContent()));
		return movieResponse;
	}

	@Override
	public MovieFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		MovieRestBeanParams movieRestBeanParams = new MovieRestBeanParams();
		movieRestBeanParams.setUid(uid);
		Page<Movie> moviePage = movieRestQuery.query(movieRestBeanParams);
		MovieFullResponse movieResponse = new MovieFullResponse();
		movieResponse.setMovie(movieFullRestMapper.mapFull(Iterables.getOnlyElement(moviePage.getContent(), null)));
		return movieResponse;
	}

}
