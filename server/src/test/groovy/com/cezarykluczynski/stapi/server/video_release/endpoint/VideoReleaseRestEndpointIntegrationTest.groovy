package com.cezarykluczynski.stapi.server.video_release.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_VIDEO_RELEASES)
})
class VideoReleaseRestEndpointIntegrationTest extends AbstractVideoReleaseEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets video release by UID"() {
		when:
		VideoReleaseFullResponse videoReleaseFullResponse = stapiRestClient.videoReleaseApi.videoReleaseGet('VIMA0000204764', null)

		then:
		videoReleaseFullResponse.videoRelease.title == 'Survive and Succeed: An Empire at War'
	}

	void "'Star Trek 50th Anniversary TV and Movie Collection' is among video release with run time of 6000 to 8000 minutes"() {
		when:
		VideoReleaseBaseResponse videoReleaseBaseResponse = stapiRestClient.videoReleaseApi.videoReleaseSearchPost(null, null, null, null, null,
				null, null, 6000, 8000)

		then:
		videoReleaseBaseResponse.videoReleases.stream()
				.anyMatch { it.title == 'Star Trek 50th Anniversary TV and Movie Collection' }
	}

}
