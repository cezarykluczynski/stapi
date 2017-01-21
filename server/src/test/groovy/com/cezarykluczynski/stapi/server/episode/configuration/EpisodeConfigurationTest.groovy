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

	void setup() {
		applicationContextMock = Mock(ApplicationContext)
		episodeConfiguration = new EpisodeConfiguration(applicationContext: applicationContextMock)
	}

	void "episode soap endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		EpisodeSoapReader episodeSoapReaderMock = Mock(EpisodeSoapReader)

		when:
		Endpoint episodeSoapEndpoint = episodeConfiguration.episodeSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> springBus
		1 * applicationContextMock.getBean(EpisodeSoapReader) >> episodeSoapReaderMock
		episodeSoapEndpoint != null
		((EndpointImpl) episodeSoapEndpoint).implementor instanceof EpisodeSoapEndpoint
		((EndpointImpl) episodeSoapEndpoint).bus == springBus
		episodeSoapEndpoint.published
	}

	void "EpisodeSoapMapper is created"() {
		when:
		EpisodeSoapMapper episodeSoapMapper = episodeConfiguration.episodeSoapMapper()

		then:
		episodeSoapMapper != null
	}

	void "EpisodeRestMapper is created"() {
		when:
		EpisodeRestMapper episodeRestMapper = episodeConfiguration.episodeRestMapper()

		then:
		episodeRestMapper != null
	}

}
