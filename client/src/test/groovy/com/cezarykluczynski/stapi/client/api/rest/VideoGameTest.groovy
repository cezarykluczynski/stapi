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
		videoGame = new VideoGame(videoGameApiMock)
	}

	void "gets single entity"() {
		given:
		VideoGameFullResponse videoGameFullResponse = Mock()

		when:
		VideoGameFullResponse videoGameFullResponseOutput = videoGame.get(UID)

		then:
		1 * videoGameApiMock.v1RestVideoGameGet(UID, null) >> videoGameFullResponse
		0 * _
		videoGameFullResponse == videoGameFullResponseOutput
	}

	void "searches entities"() {
		given:
		VideoGameBaseResponse videoGameBaseResponse = Mock()

		when:
		VideoGameBaseResponse videoGameBaseResponseOutput = videoGame.search(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE, RELEASE_DATE_FROM, RELEASE_DATE_TO)

		then:
		1 * videoGameApiMock.v1RestVideoGameSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, null, TITLE, RELEASE_DATE_FROM, RELEASE_DATE_TO) >>
				videoGameBaseResponse
		0 * _
		videoGameBaseResponse == videoGameBaseResponseOutput
	}

}
