package com.cezarykluczynski.stapi.server.video_game.configuration

import com.cezarykluczynski.stapi.server.video_game.endpoint.VideoGameRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.video_game.endpoint.VideoGameSoapEndpoint
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameBaseRestMapper
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameBaseSoapMapper
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameFullRestMapper
import com.cezarykluczynski.stapi.server.video_game.mapper.VideoGameFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class VideoGameConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private VideoGameConfiguration videoGameConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		videoGameConfiguration = new VideoGameConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "VideoGame SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = videoGameConfiguration.videoGameEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(VideoGameSoapEndpoint, VideoGameSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
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

	void "VideoGameBaseSoapMapper is created"() {
		when:
		VideoGameBaseSoapMapper videoGameBaseSoapMapper = videoGameConfiguration.videoGameBaseSoapMapper()

		then:
		videoGameBaseSoapMapper != null
	}

	void "VideoGameFullSoapMapper is created"() {
		when:
		VideoGameFullSoapMapper videoGameFullSoapMapper = videoGameConfiguration.videoGameFullSoapMapper()

		then:
		videoGameFullSoapMapper != null
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
