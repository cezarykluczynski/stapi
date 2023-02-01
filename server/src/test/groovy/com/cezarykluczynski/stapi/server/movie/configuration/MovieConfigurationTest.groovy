package com.cezarykluczynski.stapi.server.movie.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.movie.endpoint.MovieRestEndpoint
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseRestMapper
import com.cezarykluczynski.stapi.server.movie.mapper.MovieFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class MovieConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private MovieConfiguration movieConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		movieConfiguration = new MovieConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Movie REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = movieConfiguration.movieServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(MovieRestEndpoint, MovieRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "MovieBaseRestMapper is created"() {
		when:
		MovieBaseRestMapper movieBaseRestMapper = movieConfiguration.movieBaseRestMapper()

		then:
		movieBaseRestMapper != null
	}

	void "MovieFullRestMapper is created"() {
		when:
		MovieFullRestMapper movieFullRestMapper = movieConfiguration.movieFullRestMapper()

		then:
		movieFullRestMapper != null
	}

}
