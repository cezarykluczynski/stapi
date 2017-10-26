package com.cezarykluczynski.stapi.server.movie.query;

import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullRequest;
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.movie.repository.MovieRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseSoapMapper;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MovieSoapQuery {

	private final MovieBaseSoapMapper movieBaseSoapMapper;

	private final MovieFullSoapMapper movieFullSoapMapper;

	private final PageMapper pageMapper;

	private final MovieRepository movieRepository;

	public MovieSoapQuery(MovieBaseSoapMapper movieBaseSoapMapper, MovieFullSoapMapper movieFullSoapMapper, PageMapper pageMapper,
			MovieRepository movieRepository) {
		this.movieBaseSoapMapper = movieBaseSoapMapper;
		this.movieFullSoapMapper = movieFullSoapMapper;
		this.pageMapper = pageMapper;
		this.movieRepository = movieRepository;
	}

	public Page<Movie> query(MovieBaseRequest movieBaseRequest) {
		MovieRequestDTO movieRequestDTO = movieBaseSoapMapper.mapBase(movieBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(movieBaseRequest.getPage());
		return movieRepository.findMatching(movieRequestDTO, pageRequest);
	}

	public Page<Movie> query(MovieFullRequest movieFullRequest) {
		MovieRequestDTO movieRequestDTO = movieFullSoapMapper.mapFull(movieFullRequest);
		return movieRepository.findMatching(movieRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
