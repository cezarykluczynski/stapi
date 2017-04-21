package com.cezarykluczynski.stapi.etl.movie.creation.processor

import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.movie.repository.MovieRepository
import com.google.common.collect.Lists
import spock.lang.Specification

class MovieWriterTest extends Specification {

	private MovieRepository movieRepositoryMock

	private MovieWriter movieWriter

	void setup() {
		movieRepositoryMock = Mock()
		movieWriter = new MovieWriter(movieRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Movie movie = new Movie()
		List<Movie> movieList = Lists.newArrayList(movie)

		when:
		movieWriter.write(movieList)

		then:
		1 * movieRepositoryMock.save(movieList)
		0 * _
	}

}
