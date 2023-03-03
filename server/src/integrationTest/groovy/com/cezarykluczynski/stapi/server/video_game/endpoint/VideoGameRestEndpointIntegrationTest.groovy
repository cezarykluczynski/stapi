package com.cezarykluczynski.stapi.server.video_game.endpoint

import com.cezarykluczynski.stapi.client.api.dto.VideoGameSearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_VIDEO_GAMES)
})
class VideoGameRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets video game by UID"() {
		when:
		VideoGameFullResponse videoGameFullResponse = stapiRestClient.videoGame.get('VGMA0000208812')

		then:
		videoGameFullResponse.videoGame.title == 'Star Trek: Bridge Crew'
	}

	void "there are two 'Star Trek: Armada' games"() {
		given:
		VideoGameSearchCriteria videoGameSearchCriteria = new VideoGameSearchCriteria(
				title: 'Star Trek: Armada'
		)

		when:
		VideoGameBaseResponse videoGameBaseResponse = stapiRestClient.videoGame.search(videoGameSearchCriteria)

		then:
		videoGameBaseResponse.videoGames.size() == 2
	}

}
