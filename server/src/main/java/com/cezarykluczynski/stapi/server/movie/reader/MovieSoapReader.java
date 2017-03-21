package com.cezarykluczynski.stapi.server.movie.reader;

import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullResponse;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseSoapMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieFullSoapMapper;
import com.cezarykluczynski.stapi.server.movie.query.MovieSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class MovieSoapReader implements BaseReader<MovieBaseRequest, MovieBaseResponse>, FullReader<MovieFullRequest, MovieFullResponse> {

	private MovieSoapQuery movieSoapQuery;

	private MovieBaseSoapMapper movieBaseSoapMapper;

	private MovieFullSoapMapper movieFullSoapMapper;

	private PageMapper pageMapper;

	public MovieSoapReader(MovieSoapQuery movieSoapQuery, MovieBaseSoapMapper movieBaseSoapMapper, MovieFullSoapMapper movieFullSoapMapper,
			PageMapper pageMapper) {
		this.movieSoapQuery = movieSoapQuery;
		this.movieBaseSoapMapper = movieBaseSoapMapper;
		this.movieFullSoapMapper = movieFullSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public MovieBaseResponse readBase(MovieBaseRequest input) {
		Page<Movie> moviePage = movieSoapQuery.query(input);
		MovieBaseResponse movieResponse = new MovieBaseResponse();
		movieResponse.setPage(pageMapper.fromPageToSoapResponsePage(moviePage));
		movieResponse.getMovies().addAll(movieBaseSoapMapper.mapBase(moviePage.getContent()));
		return movieResponse;
	}

	@Override
	public MovieFullResponse readFull(MovieFullRequest input) {
		Page<Movie> moviePage = movieSoapQuery.query(input);
		MovieFullResponse movieFullResponse = new MovieFullResponse();
		movieFullResponse.setMovie(movieFullSoapMapper.mapFull(Iterables.getOnlyElement(moviePage.getContent(), null)));
		return movieFullResponse;
	}

}
