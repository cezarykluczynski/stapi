package com.cezarykluczynski.stapi.server.video_game.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBase
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.VideoGameFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_VIDEO_GAMES)
})
class VideoGameSoapEndpointIntegrationTest extends AbstractVideoGameEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets video game by UID"() {
		when:
		VideoGameFullResponse videoGameFullResponse = stapiSoapClient.videoGamePortType.getVideoGameFull(new VideoGameFullRequest(
				uid: 'VGMA0000111268'
		))

		then:
		videoGameFullResponse.videoGame.title == 'Star Trek: Tactical Assault'
	}

	void "gets video game by title"() {
		when:
		VideoGameBaseResponse videoGameBaseResponse = stapiSoapClient.videoGamePortType.getVideoGameBase(new VideoGameBaseRequest(
				title: 'Star Trek: The Game Show'
		))
		List<VideoGameBase> videoGameBaseList = videoGameBaseResponse.videoGames

		then:
		videoGameBaseList.size() == 1
		videoGameBaseList[0].uid == 'VGMA0000015824'
	}

}
