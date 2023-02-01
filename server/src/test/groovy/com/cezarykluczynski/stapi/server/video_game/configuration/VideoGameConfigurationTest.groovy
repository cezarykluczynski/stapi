package com.cezarykluczynski.stapi.server.video_game.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.video_game.endpoint.VideoGameRestEndpoint
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameBaseRestMapper
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class VideoGameConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private VideoGameConfiguration videoGameConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		videoGameConfiguration = new VideoGameConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "VideoGame REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = videoGameConfiguration.videoGameServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(VideoGameRestEndpoint, VideoGameRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "VideoGameBaseRestMapper is created"() {
		when:
		VideoGameBaseRestMapper videoGameBaseRestMapper = videoGameConfiguration.videoGameBaseRestMapper()

		then:
		videoGameBaseRestMapper != null
	}

	void "VideoGameFullRestMapper is created"() {
		when:
		VideoGameFullRestMapper videoGameFullRestMapper = videoGameConfiguration.videoGameFullRestMapper()

		then:
		videoGameFullRestMapper != null
	}

}
