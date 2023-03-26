package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.VideoGameApi
import com.cezarykluczynski.stapi.client.rest.model.VideoGameBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.VideoGameFullResponse
import com.cezarykluczynski.stapi.client.rest.model.VideoGameSearchCriteria
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
		1 * videoGameApiMock.v1GetVideoGame(UID) >> videoGameFullResponse
		0 * _
		videoGameFullResponse == videoGameFullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		VideoGameBaseResponse videoGameBaseResponse = Mock()
		VideoGameSearchCriteria videoGameSearchCriteria = new VideoGameSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				title: TITLE,
				releaseDateFrom: RELEASE_DATE_FROM,
				releaseDateTo: RELEASE_DATE_TO)
		videoGameSearchCriteria.sort = SORT

		when:
		VideoGameBaseResponse videoGameBaseResponseOutput = videoGame.search(videoGameSearchCriteria)

		then:
		1 * videoGameApiMock.v1SearchVideoGames(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, RELEASE_DATE_FROM, RELEASE_DATE_TO) >>
				videoGameBaseResponse
		0 * _
		videoGameBaseResponse == videoGameBaseResponseOutput
	}

}
