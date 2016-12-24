package com.cezarykluczynski.stapi.etl.movie.creation.processor

import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.movie.repository.MovieRepository
import com.google.common.collect.Lists
import spock.lang.Specification

class MovieWriterTest extends Specification {

	private MovieRepository movieRepositoryMock

	private MovieWriter movieWriter

	def setup() {
		movieRepositoryMock = Mock(MovieRepository)
		movieWriter = new MovieWriter(movieRepositoryMock)
	}

	def "writes all entities using repository"() {
		given:
		Movie Movie = new Movie()
		List<Movie> movieList = Lists.newArrayList(Movie)

		when:
		movieWriter.write(movieList)

		then:
		1 * movieRepositoryMock.save(movieList)
		0 * _
	}

}
