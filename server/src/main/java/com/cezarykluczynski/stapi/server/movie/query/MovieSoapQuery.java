package com.cezarykluczynski.stapi.server.movie.query;

import com.cezarykluczynski.stapi.client.v1.soap.MovieRequest;
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.movie.repository.MovieRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class MovieSoapQuery {

	private MovieSoapMapper movieSoapMapper;

	private PageMapper pageMapper;

	private MovieRepository movieRepository;

	@Inject
	public MovieSoapQuery(MovieSoapMapper movieSoapMapper, PageMapper pageMapper, MovieRepository movieRepository) {
		this.movieSoapMapper = movieSoapMapper;
		this.pageMapper = pageMapper;
		this.movieRepository = movieRepository;
	}

	public Page<Movie> query(MovieRequest movieRequest) {
		MovieRequestDTO movieRequestDTO = movieSoapMapper.map(movieRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(movieRequest.getPage());
		return movieRepository.findMatching(movieRequestDTO, pageRequest);
	}

}
