package com.cezarykluczynski.stapi.server.movie.configuration

import com.cezarykluczynski.stapi.server.movie.endpoint.MovieRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.movie.endpoint.MovieSoapEndpoint
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseRestMapper
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseSoapMapper
import com.cezarykluczynski.stapi.server.movie.mapper.MovieFullRestMapper
import com.cezarykluczynski.stapi.server.movie.mapper.MovieFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class MovieConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private MovieConfiguration movieConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		movieConfiguration = new MovieConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Movie SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = movieConfiguration.movieEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(MovieSoapEndpoint, MovieSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
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

	void "MovieBaseSoapMapper is created"() {
		when:
		MovieBaseSoapMapper movieBaseSoapMapper = movieConfiguration.movieBaseSoapMapper()

		then:
		movieBaseSoapMapper != null
	}

	void "MovieFullSoapMapper is created"() {
		when:
		MovieFullSoapMapper movieFullSoapMapper = movieConfiguration.movieFullSoapMapper()

		then:
		movieFullSoapMapper != null
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
