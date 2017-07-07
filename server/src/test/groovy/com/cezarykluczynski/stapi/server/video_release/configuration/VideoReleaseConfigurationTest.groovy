package com.cezarykluczynski.stapi.server.video_release.configuration

import com.cezarykluczynski.stapi.server.video_release.endpoint.VideoReleaseRestEndpoint
import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.video_release.endpoint.VideoReleaseSoapEndpoint
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseBaseRestMapper
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseBaseSoapMapper
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseFullRestMapper
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseFullSoapMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

import javax.xml.ws.Endpoint

class VideoReleaseConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private VideoReleaseConfiguration videoReleaseConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		videoReleaseConfiguration = new VideoReleaseConfiguration(endpointFactory: endpointFactoryMock)
	}

	void "VideoRelease SOAP endpoint is created"() {
		given:
		Endpoint endpoint = Mock()

		when:
		Endpoint endpointOutput = videoReleaseConfiguration.videoReleaseEndpoint()

		then:
		1 * endpointFactoryMock.createSoapEndpoint(VideoReleaseSoapEndpoint, VideoReleaseSoapEndpoint.ADDRESS) >> endpoint
		0 * _
		endpointOutput == endpoint
	}

	void "VideoRelease REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = videoReleaseConfiguration.videoReleaseServer()

		then:
		1 * endpointFactoryMock.createRestEndpoint(VideoReleaseRestEndpoint, VideoReleaseRestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
	}

	void "VideoReleaseBaseSoapMapper is created"() {
		when:
		VideoReleaseBaseSoapMapper videoReleaseBaseSoapMapper = videoReleaseConfiguration.videoReleaseBaseSoapMapper()

		then:
		videoReleaseBaseSoapMapper != null
	}

	void "VideoReleaseFullSoapMapper is created"() {
		when:
		VideoReleaseFullSoapMapper videoReleaseFullSoapMapper = videoReleaseConfiguration.videoReleaseFullSoapMapper()

		then:
		videoReleaseFullSoapMapper != null
	}

	void "VideoReleaseBaseRestMapper is created"() {
		when:
		VideoReleaseBaseRestMapper videoReleaseBaseRestMapper = videoReleaseConfiguration.videoReleaseBaseRestMapper()

		then:
		videoReleaseBaseRestMapper != null
	}

	void "VideoReleaseFullRestMapper is created"() {
		when:
		VideoReleaseFullRestMapper videoReleaseFullRestMapper = videoReleaseConfiguration.videoReleaseFullRestMapper()

		then:
		videoReleaseFullRestMapper != null
	}

}
