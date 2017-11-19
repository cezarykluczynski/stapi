package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.VideoGameApi
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.VideoGameFullResponse
import com.cezarykluczynski.stapi.util.AbstractVideoGameTest

class VideoGameTest extends AbstractVideoGameTest {

	private VideoGameApi videoGameApiMock

	private VideoGame videoGame

	void setup() {
		videoGameApiMock = Mock()
		videoGame = new VideoGame(videoGameApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		VideoGameFullResponse videoGameFullResponse = Mock()

		when:
		VideoGameFullResponse videoGameFullResponseOutput = videoGame.get(UID)

		then:
		1 * videoGameApiMock.videoGameGet(UID, API_KEY) >> videoGameFullResponse
		0 * _
		videoGameFullResponse == videoGameFullResponseOutput
	}

	void "searches entities"() {
		given:
		VideoGameBaseResponse videoGameBaseResponse = Mock()

		when:
		VideoGameBaseResponse videoGameBaseResponseOutput = videoGame.search(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE, RELEASE_DATE_FROM, RELEASE_DATE_TO)

		then:
		1 * videoGameApiMock.videoGameSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, TITLE, RELEASE_DATE_FROM, RELEASE_DATE_TO) >>
				videoGameBaseResponse
		0 * _
		videoGameBaseResponse == videoGameBaseResponseOutput
	}

}
