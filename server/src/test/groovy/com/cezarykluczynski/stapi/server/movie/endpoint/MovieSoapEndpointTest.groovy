package com.cezarykluczynski.stapi.server.movie.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.MovieRequest
import com.cezarykluczynski.stapi.client.v1.soap.MovieResponse
import com.cezarykluczynski.stapi.server.movie.reader.MovieSoapReader
import spock.lang.Specification

class MovieSoapEndpointTest extends Specification {

	private MovieSoapReader movieSoapReaderMock

	private MovieSoapEndpoint movieSoapEndpoint

	def setup() {
		movieSoapReaderMock = Mock(MovieSoapReader)
		movieSoapEndpoint = new MovieSoapEndpoint(movieSoapReaderMock)
	}

	def "passes call to MovieSoapReader"() {
		given:
		MovieRequest movieRequest = Mock(MovieRequest)
		MovieResponse movieResponse = Mock(MovieResponse)

		when:
		MovieResponse movieResponseResult = movieSoapEndpoint.getMovies(movieRequest)

		then:
		1 * movieSoapReaderMock.read(movieRequest) >> movieResponse
		movieResponseResult == movieResponse
	}

}
