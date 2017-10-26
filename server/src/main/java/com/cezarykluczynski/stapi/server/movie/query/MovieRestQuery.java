package com.cezarykluczynski.stapi.server.movie.query;

import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.movie.repository.MovieRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.movie.dto.MovieRestBeanParams;
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MovieRestQuery {

	private final MovieBaseRestMapper movieBaseRestMapper;

	private final PageMapper pageMapper;

	private final MovieRepository movieRepository;

	public MovieRestQuery(MovieBaseRestMapper movieBaseRestMapper, PageMapper pageMapper, MovieRepository movieRepository) {
		this.movieBaseRestMapper = movieBaseRestMapper;
		this.pageMapper = pageMapper;
		this.movieRepository = movieRepository;
	}

	public Page<Movie> query(MovieRestBeanParams movieRestBeanParams) {
		MovieRequestDTO movieRequestDTO = movieBaseRestMapper.mapBase(movieRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(movieRestBeanParams);
		return movieRepository.findMatching(movieRequestDTO, pageRequest);
	}

}
