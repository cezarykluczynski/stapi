package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.MoviePortType
import spock.lang.Specification

class MovieTest extends Specification {

	private MoviePortType moviePortTypeMock

	private Movie movie

	void setup() {
		moviePortTypeMock = Mock()
		movie = new Movie(moviePortTypeMock)
	}

	void "gets single entity"() {
		given:
		MovieBaseRequest movieBaseRequest = Mock()
		MovieBaseResponse movieBaseResponse = Mock()

		when:
		MovieBaseResponse movieBaseResponseOutput = movie.search(movieBaseRequest)

		then:
		1 * moviePortTypeMock.getMovieBase(movieBaseRequest) >> movieBaseResponse
		0 * _
		movieBaseResponse == movieBaseResponseOutput
	}

	void "searches entities"() {
		given:
		MovieFullRequest movieFullRequest = Mock()
		MovieFullResponse movieFullResponse = Mock()

		when:
		MovieFullResponse movieFullResponseOutput = movie.get(movieFullRequest)

		then:
		1 * moviePortTypeMock.getMovieFull(movieFullRequest) >> movieFullResponse
		0 * _
		movieFullResponse == movieFullResponseOutput
	}

}
