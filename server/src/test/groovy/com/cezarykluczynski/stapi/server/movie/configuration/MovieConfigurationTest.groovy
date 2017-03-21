package com.cezarykluczynski.stapi.server.movie.configuration

import com.cezarykluczynski.stapi.server.movie.endpoint.MovieRestEndpoint
import com.cezarykluczynski.stapi.server.movie.endpoint.MovieSoapEndpoint
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseRestMapper
import com.cezarykluczynski.stapi.server.movie.mapper.MovieBaseSoapMapper
import com.cezarykluczynski.stapi.server.movie.mapper.MovieFullRestMapper
import com.cezarykluczynski.stapi.server.movie.mapper.MovieFullSoapMapper
import com.cezarykluczynski.stapi.server.movie.reader.MovieRestReader
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
		MovieBaseRestMapper movieRestMapper = movieConfiguration.movieBaseRestMapper()

		then:
		movieRestMapper != null
	}

	void "MovieFullRestMapper is created"() {
		when:
		MovieFullRestMapper movieFullRestMapper = movieConfiguration.movieFullRestMapper()

		then:
		movieFullRestMapper != null
	}

}
