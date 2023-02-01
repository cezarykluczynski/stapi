package com.cezarykluczynski.stapi.server.episode.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.episode.endpoint.EpisodeRestEndpoint
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeBaseRestMapper
import com.cezarykluczynski.stapi.server.episode.mapper.EpisodeFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class EpisodeConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private EpisodeConfiguration episodeConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		episodeConfiguration = new EpisodeConfiguration(endpointFactory: endpointFactoryMock)
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
