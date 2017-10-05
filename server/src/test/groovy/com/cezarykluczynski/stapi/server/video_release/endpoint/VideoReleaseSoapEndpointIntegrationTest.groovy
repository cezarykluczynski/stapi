package com.cezarykluczynski.stapi.server.video_release.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBase
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.VideoReleaseFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_VIDEO_RELEASES)
})
class VideoReleaseSoapEndpointIntegrationTest extends AbstractVideoReleaseEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets video release by UID"() {
		when:
		VideoReleaseFullResponse videoReleaseFullResponse = stapiSoapClient.videoReleasePortType.getVideoReleaseFull(new VideoReleaseFullRequest(
				uid: 'VIMA0000132054'
		))

		then:
		videoReleaseFullResponse.videoRelease.title == 'Star Trek 25th Anniversary Special'
	}

	void "gets video release by title"() {
		when:
		VideoReleaseBaseResponse videoReleaseBaseResponse = stapiSoapClient.videoReleasePortType.getVideoReleaseBase(new VideoReleaseBaseRequest(
				title: 'To Be Takei'
		))
		List<VideoReleaseBase> videoReleaseBaseList = videoReleaseBaseResponse.videoReleases

		then:
		videoReleaseBaseList.size() == 1
		videoReleaseBaseList[0].title == 'To Be Takei'
	}

}
