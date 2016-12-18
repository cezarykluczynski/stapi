package com.cezarykluczynski.stapi.server.episode.configuration

import com.cezarykluczynski.stapi.server.episode.endpoint.EpisodeSoapEndpoint
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeRestMapper
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeSoapMapper
import com.cezarykluczynski.stapi.server.episode.reader.EpisodeSoapReader
import org.apache.cxf.bus.spring.SpringBus
import org.apache.cxf.jaxws.EndpointImpl
import org.springframework.context.ApplicationContext
import spock.lang.Specification

import javax.xml.ws.Endpoint

class EpisodeConfigurationTest extends Specification {

	private ApplicationContext applicationContextMock

	private EpisodeConfiguration episodeConfiguration

	def setup() {
		applicationContextMock = Mock(ApplicationContext)
		episodeConfiguration = new EpisodeConfiguration()
		episodeConfiguration.applicationContext = applicationContextMock
	}

	def "episode soap endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		EpisodeSoapReader episodeSoapReaderMock = Mock(EpisodeSoapReader)

		when:
		Endpoint episodeSoapEndpoint = episodeConfiguration.episodeSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus.class) >> springBus
		1 * applicationContextMock.getBean(EpisodeSoapReader.class) >> episodeSoapReaderMock
		episodeSoapEndpoint != null
		((EndpointImpl) episodeSoapEndpoint).implementor instanceof EpisodeSoapEndpoint
		((EndpointImpl) episodeSoapEndpoint).bus == springBus
		episodeSoapEndpoint.published
	}

	def "EpisodeSoapMapper is created"() {
		when:
		EpisodeSoapMapper episodeSoapMapper = episodeConfiguration.episodeSoapMapper()

		then:
		episodeSoapMapper != null
	}

	def "EpisodeRestMapper is created"() {
		when:
		EpisodeRestMapper episodeRestMapper = episodeConfiguration.episodeRestMapper()

		then:
		episodeRestMapper != null
	}

}
