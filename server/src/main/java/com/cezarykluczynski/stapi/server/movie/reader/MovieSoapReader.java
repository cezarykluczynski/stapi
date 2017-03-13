package com.cezarykluczynski.stapi.server.movie.reader;

import com.cezarykluczynski.stapi.client.v1.soap.MovieRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MovieResponse;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieSoapMapper;
import com.cezarykluczynski.stapi.server.movie.query.MovieSoapQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class MovieSoapReader implements BaseReader<MovieRequest, MovieResponse> {

	private MovieSoapQuery movieSoapQuery;

	private MovieSoapMapper movieSoapMapper;

	private PageMapper pageMapper;

	public MovieSoapReader(MovieSoapQuery movieSoapQuery, MovieSoapMapper movieSoapMapper, PageMapper pageMapper) {
		this.movieSoapQuery = movieSoapQuery;
		this.movieSoapMapper = movieSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public MovieResponse readBase(MovieRequest input) {
		Page<Movie> moviePage = movieSoapQuery.query(input);
		MovieResponse movieResponse = new MovieResponse();
		movieResponse.setPage(pageMapper.fromPageToSoapResponsePage(moviePage));
		movieResponse.getMovies().addAll(movieSoapMapper.map(moviePage.getContent()));
		return movieResponse;
	}

}
