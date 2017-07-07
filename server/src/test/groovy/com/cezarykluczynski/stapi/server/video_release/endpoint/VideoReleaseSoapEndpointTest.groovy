package com.cezarykluczynski.stapi.server.video_release.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFullResponse
import com.cezarykluczynski.stapi.server.video_release.reader.VideoReleaseSoapReader
import spock.lang.Specification

class VideoReleaseSoapEndpointTest extends Specification {

	private VideoReleaseSoapReader videoReleaseSoapReaderMock

	private VideoReleaseSoapEndpoint videoReleaseSoapEndpoint

	void setup() {
		videoReleaseSoapReaderMock = Mock()
		videoReleaseSoapEndpoint = new VideoReleaseSoapEndpoint(videoReleaseSoapReaderMock)
	}

	void "passes base call to VideoReleaseSoapReader"() {
		given:
		VideoReleaseBaseRequest videoReleaseBaseRequest = Mock()
		VideoReleaseBaseResponse videoReleaseBaseResponse = Mock()

		when:
		VideoReleaseBaseResponse videoReleaseResponseResult = videoReleaseSoapEndpoint.getVideoReleaseBase(videoReleaseBaseRequest)

		then:
		1 * videoReleaseSoapReaderMock.readBase(videoReleaseBaseRequest) >> videoReleaseBaseResponse
		videoReleaseResponseResult == videoReleaseBaseResponse
	}

	void "passes full call to VideoReleaseSoapReader"() {
		given:
		VideoReleaseFullRequest videoReleaseFullRequest = Mock()
		VideoReleaseFullResponse videoReleaseFullResponse = Mock()

		when:
		VideoReleaseFullResponse videoReleaseResponseResult = videoReleaseSoapEndpoint.getVideoReleaseFull(videoReleaseFullRequest)

		then:
		1 * videoReleaseSoapReaderMock.readFull(videoReleaseFullRequest) >> videoReleaseFullResponse
		videoReleaseResponseResult == videoReleaseFullResponse
	}

}
