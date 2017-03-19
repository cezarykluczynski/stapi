package com.cezarykluczynski.stapi.server.episode.configuration

import com.cezarykluczynski.stapi.server.episode.endpoint.EpisodeRestEndpoint
import com.cezarykluczynski.stapi.server.episode.endpoint.EpisodeSoapEndpoint
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseRestMapper
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseSoapMapper
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeFullRestMapper
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeFullSoapMapper
import com.cezarykluczynski.stapi.server.episode.reader.EpisodeRestReader
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

	void "Episode SOAP endpoint is created"() {
		given:
		SpringBus springBus = new SpringBus()
		EpisodeSoapReader episodeSoapReaderMock = Mock(EpisodeSoapReader)

		when:
		Endpoint episodeSoapEndpoint = episodeConfiguration.episodeSoapEndpoint()

		then:
		1 * applicationContextMock.getBean(SpringBus) >> springBus
		1 * applicationContextMock.getBean(EpisodeSoapReader) >> episodeSoapReaderMock
		0 * _
		episodeSoapEndpoint != null
		((EndpointImpl) episodeSoapEndpoint).implementor instanceof EpisodeSoapEndpoint
		((EndpointImpl) episodeSoapEndpoint).bus == springBus
		episodeSoapEndpoint.published
	}

	void "EpisodeRestEndpoint is created"() {
		given:
		EpisodeRestReader episodeRestMapper = Mock(EpisodeRestReader)

		when:
		EpisodeRestEndpoint episodeRestEndpoint = episodeConfiguration.episodeRestEndpoint()

		then:
		1 * applicationContextMock.getBean(EpisodeRestReader) >> episodeRestMapper
		0 * _
		episodeRestEndpoint != null
		episodeRestEndpoint.episodeRestReader == episodeRestMapper
	}

	void "EpisodeSoapBaseMapper is created"() {
		when:
		EpisodeBaseSoapMapper episodeSoapMapper = episodeConfiguration.episodeBaseSoapMapper()

		then:
		episodeSoapMapper != null
	}

	void "EpisodeFullSoapMapper is created"() {
		when:
		EpisodeFullSoapMapper episodeFullSoapMapper = episodeConfiguration.episodeFullSoapMapper()

		then:
		episodeFullSoapMapper != null
	}

	void "EpisodeBaseRestMapper is created"() {
		when:
		EpisodeBaseRestMapper episodeBaseRestMapper = episodeConfiguration.episodeBaseRestMapper()

		then:
		episodeBaseRestMapper != null
	}

	void "EpisodeFullRestMapper is created"() {
		when:
		EpisodeFullRestMapper episodeFullRestMapper = episodeConfiguration.episodeFullRestMapper()

		then:
		episodeFullRestMapper != null
	}

}
