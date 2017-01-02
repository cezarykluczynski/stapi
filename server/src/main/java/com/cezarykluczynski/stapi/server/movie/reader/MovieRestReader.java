package com.cezarykluczynski.stapi.server.movie.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.MovieResponse;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import com.cezarykluczynski.stapi.server.movie.dto.MovieRestBeanParams;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieRestMapper;
import com.cezarykluczynski.stapi.server.movie.query.MovieRestQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class MovieRestReader implements Reader<MovieRestBeanParams, MovieResponse> {

	private MovieRestQuery movieRestQuery;

	private MovieRestMapper movieRestMapper;

	private PageMapper pageMapper;

	@Inject
	public MovieRestReader(MovieRestQuery movieRestQuery, MovieRestMapper movieRestMapper,
			PageMapper pageMapper) {
		this.movieRestQuery = movieRestQuery;
		this.movieRestMapper = movieRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public MovieResponse read(MovieRestBeanParams input) {
		Page<Movie> moviePage = movieRestQuery.query(input);
		MovieResponse movieResponse = new MovieResponse();
		movieResponse.setPage(pageMapper.fromPageToRestResponsePage(moviePage));
		movieResponse.getMovies().addAll(movieRestMapper.map(moviePage.getContent()));
		return movieResponse;
	}

}
