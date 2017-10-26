package com.cezarykluczynski.stapi.etl.movie.creation.processor;

import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.movie.repository.MovieRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieWriter implements ItemWriter<Movie> {

	private final MovieRepository movieRepository;

	public MovieWriter(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Override
	public void write(List<? extends Movie> items) throws Exception {
		movieRepository.save(items);
	}

}
