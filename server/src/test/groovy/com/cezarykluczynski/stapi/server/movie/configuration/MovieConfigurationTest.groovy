package com.cezarykluczynski.stapi.server.movie.configuration

import com.cezarykluczynski.stapi.server.movie.endpoint.MovieRestEndpoint
import com.cezarykluczynski.stapi.server.movie.reader.MovieRestReader
import com.cezarykluczynski.stapi.server.movie.endpoint.MovieSoapEndpoint
import com.cezarykluczynski.stapi.server.movie.mapper.MovieRestMapper
import com.cezarykluczynski.stapi.server.movie.mapper.MovieSoapMapper
import com.cezarykluczynski.stapi.server.movie.reader.MovieSoapReader
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.xml.ws.Endpoint

class MovieConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private MovieConfiguration movieConfiguration

	void setup() {
		applicationContextMock = Mock(ApplicationContext)
		movieConfiguration = new MovieConfiguration(applicationContext: applicationContextMock)
	}

	void "Movie SOAP endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		MovieSoapReader movieSoapReaderMock = Mock(MovieSoapReader)

		when:
		Endpoint movieSoapEndpoint = movieConfiguration.movieSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> springBus
		1 * applicationContextMock.getBean(MovieSoapReader) >> movieSoapReaderMock
		0 * _
		movieSoapEndpoint != null
		((EndpointImpl) movieSoapEndpoint).implementor instanceof MovieSoapEndpoint
		((EndpointImpl) movieSoapEndpoint).bus == springBus
		movieSoapEndpoint.published
	}

	void "MovieRestEndpoint is created"() {
		given:
		MovieRestReader movieRestMapper = Mock(MovieRestReader)

		when:
		MovieRestEndpoint movieRestEndpoint = movieConfiguration.movieRestEndpoint()

		then:
		1 * applicationContextMock.getBean(MovieRestReader) >> movieRestMapper
		0 * _
		movieRestEndpoint != null
		movieRestEndpoint.movieRestReader == movieRestMapper
	}

	void "MovieSoapMapper is created"() {
		when:
		MovieSoapMapper movieSoapMapper = movieConfiguration.movieSoapMapper()

		then:
		movieSoapMapper != null
	}

	void "MovieRestMapper is created"() {
		when:
		MovieRestMapper movieRestMapper = movieConfiguration.movieRestMapper()

		then:
		movieRestMapper != null
	}

}
