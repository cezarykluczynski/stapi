package com.cezarykluczynski.stapi.server.movie.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MovieBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.MovieFullResponse
import com.cezarykluczynski.stapi.server.movie.reader.MovieSoapReader
import spock.lang.Specification

class MovieSoapEndpointTest extends Specification {

	private MovieSoapReader movieSoapReaderMock

	private MovieSoapEndpoint movieSoapEndpoint

	void setup() {
		movieSoapReaderMock = Mock()
		movieSoapEndpoint = new MovieSoapEndpoint(movieSoapReaderMock)
	}

	void "passes base call to MovieSoapReader"() {
		given:
		MovieBaseRequest movieBaseRequest = Mock()
		MovieBaseResponse movieBaseResponse = Mock()

		when:
		MovieBaseResponse movieResponseResult = movieSoapEndpoint.getMovieBase(movieBaseRequest)

		then:
		1 * movieSoapReaderMock.readBase(movieBaseRequest) >> movieBaseResponse
		movieResponseResult == movieBaseResponse
	}

	void "passes full call to MovieSoapReader"() {
		given:
		MovieFullRequest movieFullRequest = Mock()
		MovieFullResponse movieFullResponse = Mock()

		when:
		MovieFullResponse movieResponseResult = movieSoapEndpoint.getMovieFull(movieFullRequest)

		then:
		1 * movieSoapReaderMock.readFull(movieFullRequest) >> movieFullResponse
		movieResponseResult == movieFullResponse
	}

}
