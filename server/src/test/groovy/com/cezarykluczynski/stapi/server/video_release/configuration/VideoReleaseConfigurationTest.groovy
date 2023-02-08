package com.cezarykluczynski.stapi.server.video_release.configuration

import com.cezarykluczynski.stapi.server.common.endpoint.EndpointFactory
import com.cezarykluczynski.stapi.server.video_release.endpoint.VideoReleaseRestEndpoint
import com.cezarykluczynski.stapi.server.video_release.endpoint.VideoReleaseV2RestEndpoint
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseBaseRestMapper
import com.cezarykluczynski.stapi.server.video_release.mapper.VideoReleaseFullRestMapper
import org.apache.cxf.endpoint.Server
import spock.lang.Specification

class VideoReleaseConfigurationTest extends Specification {

	private EndpointFactory endpointFactoryMock

	private VideoReleaseConfiguration videoReleaseConfiguration

	void setup() {
		endpointFactoryMock = Mock()
		videoReleaseConfiguration = new VideoReleaseConfiguration(endpointFactory: endpointFactoryMock)
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

	void "VideoRelease V2 REST endpoint is created"() {
		given:
		Server server = Mock()

		when:
		Server serverOutput = videoReleaseConfiguration.videoReleaseV2Server()

		then:
		1 * endpointFactoryMock.createRestEndpoint(VideoReleaseV2RestEndpoint, VideoReleaseV2RestEndpoint.ADDRESS) >> server
		0 * _
		serverOutput == server
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
