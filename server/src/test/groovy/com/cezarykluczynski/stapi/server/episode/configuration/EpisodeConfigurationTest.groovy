package com.cezarykluczynski.stapi.server.episode.configuration

import com.cezarykluczynski.stapi.server.episode.endpoint.EpisodeRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.episode.endpoint.EpisodeSoapEndpoint
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseRestMapper
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseSoapMapper
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeFullRestMapper
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class EpisodeConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private EpisodeConfiguration episodeConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		episodeConfiguration = new EpisodeConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "Episode SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = episodeConfiguration.episodeEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(EpisodeSoapEndpoint, EpisodeSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "Episode REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = episodeConfiguration.episodeServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(EpisodeRestEndpoint, EpisodeRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "EpisodeBaseSoapMapper is created"() {
		when:
		EpisodeBaseSoapMapper episodeBaseSoapMapper = episodeConfiguration.episodeBaseSoapMapper()

		then:
		episodeBaseSoapMapper != null
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
