package com.cezarykluczynski.stapi.client.api.soap

import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleasePortType
import spock.lang.Specification

class VideoReleaseTest extends Specification {

	private VideoReleasePortType videoReleasePortTypeMock

	private VideoRelease videoRelease

	void setup() {
		videoReleasePortTypeMock = Mock()
		videoRelease = new VideoRelease(videoReleasePortTypeMock)
	}

	void "gets single entity"() {
		given:
		VideoReleaseBaseRequest videoReleaseBaseRequest = Mock()
		VideoReleaseBaseResponse videoReleaseBaseResponse = Mock()

		when:
		VideoReleaseBaseResponse videoReleaseBaseResponseOutput = videoRelease.search(videoReleaseBaseRequest)

		then:
		1 * videoReleasePortTypeMock.getVideoReleaseBase(videoReleaseBaseRequest) >> videoReleaseBaseResponse
		0 * _
		videoReleaseBaseResponse == videoReleaseBaseResponseOutput
	}

	void "searches entities"() {
		given:
		VideoReleaseFullRequest videoReleaseFullRequest = Mock()
		VideoReleaseFullResponse videoReleaseFullResponse = Mock()

		when:
		VideoReleaseFullResponse videoReleaseFullResponseOutput = videoRelease.get(videoReleaseFullRequest)

		then:
		1 * videoReleasePortTypeMock.getVideoReleaseFull(videoReleaseFullRequest) >> videoReleaseFullResponse
		0 * _
		videoReleaseFullResponse == videoReleaseFullResponseOutput
	}

}
