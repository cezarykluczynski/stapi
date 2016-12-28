package com.cezarykluczynski.stapi.etl.movie.creation.processor;

import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.movie.repository.MovieRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class MovieWriter implements ItemWriter<Movie> {

	private MovieRepository movieRepository;

	@Inject
	public MovieWriter(MovieRepository MovieRepository) {
		this.movieRepository = MovieRepository;
	}

	@Override
	public void write(List<? extends Movie> items) throws Exception {
		movieRepository.save(items);
	}

}
