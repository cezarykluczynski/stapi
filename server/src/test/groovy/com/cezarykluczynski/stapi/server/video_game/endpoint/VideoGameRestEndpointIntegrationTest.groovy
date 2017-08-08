package com.cezarykluczynski.stapi.server.video_game.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_VIDEO_GAMES)
})
class VideoGameRestEndpointIntegrationTest extends AbstractVideoGameEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets video game by UID"() {
		when:
		VideoGameFullResponse videoGameFullResponse = stapiRestClient.videoGameApi.videoGameGet('VGMA0000208812', null)

		then:
		videoGameFullResponse.videoGame.title == 'Star Trek: Bridge Crew'
	}

	void "there are two 'Star Trek: Armada' games"() {
		when:
		VideoGameBaseResponse videoGameBaseResponse = stapiRestClient.videoGameApi.videoGameSearchPost(null, null, null, null, 'Star Trek: Armada',
				null, null)

		then:
		videoGameBaseResponse.videoGames.size() == 2
	}

}
