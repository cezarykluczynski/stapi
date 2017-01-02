package com.cezarykluczynski.stapi.server.movie.configuration

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

	def setup() {
		applicationContextMock = Mock(ApplicationContext)
		movieConfiguration = new MovieConfiguration(applicationContext: applicationContextMock)
	}

	def "movie soap endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		MovieSoapReader movieSoapReaderMock = Mock(MovieSoapReader)

		when:
		Endpoint movieSoapEndpoint = movieConfiguration.movieSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus.class) >> springBus
		1 * applicationContextMock.getBean(MovieSoapReader.class) >> movieSoapReaderMock
		movieSoapEndpoint != null
		((EndpointImpl) movieSoapEndpoint).implementor instanceof MovieSoapEndpoint
		((EndpointImpl) movieSoapEndpoint).bus == springBus
		movieSoapEndpoint.published
	}

	def "MovieSoapMapper is created"() {
		when:
		MovieSoapMapper movieSoapMapper = movieConfiguration.movieSoapMapper()

		then:
		movieSoapMapper != null
	}

	def "MovieRestMapper is created"() {
		when:
		MovieRestMapper movieRestMapper = movieConfiguration.movieRestMapper()

		then:
		movieRestMapper != null
	}

}
