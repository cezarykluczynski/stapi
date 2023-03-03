package com.cezarykluczynski.stapi.server.video_release.endpoint

import com.cezarykluczynski.stapi.client.api.dto.VideoReleaseV2SearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoReleaseV2FullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_VIDEO_RELEASES)
})
class VideoReleaseRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets video release by UID"() {
		when:
		VideoReleaseV2FullResponse videoReleaseV2FullResponse = stapiRestClient.videoRelease.getV2('VIMA0000216125')

		then:
		videoReleaseV2FullResponse.videoRelease.title == '50 Years of Star Trek'
	}

	void "'Star Trek 50th Anniversary TV and Movie Collection' is among video release with run time of 6000 to 8000 minutes"() {
		given:
		VideoReleaseV2SearchCriteria videoReleaseV2SearchCriteria = new VideoReleaseV2SearchCriteria(
				runTimeFrom: 6000,
				runTimeTo: 8000
		)

		when:
		VideoReleaseV2BaseResponse videoReleaseV2BaseResponse = stapiRestClient.videoRelease.searchV2(videoReleaseV2SearchCriteria)

		then:
		videoReleaseV2BaseResponse.videoReleases.stream()
				.anyMatch { it.title == 'Star Trek 50th Anniversary TV and Movie Collection' }
	}

}
