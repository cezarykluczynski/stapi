package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.SeasonApi
import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.SeasonFullResponse
import com.cezarykluczynski.stapi.util.AbstractSeasonTest

class SeasonTest extends AbstractSeasonTest {

	private SeasonApi seasonApiMock

	private Season season

	void setup() {
		seasonApiMock = Mock()
		season = new Season(seasonApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		SeasonFullResponse seasonFullResponse = Mock()

		when:
		SeasonFullResponse seasonFullResponseOutput = season.get(UID)

		then:
		1 * seasonApiMock.seasonGet(UID, API_KEY) >> seasonFullResponse
		0 * _
		seasonFullResponse == seasonFullResponseOutput
	}

	void "searches entities"() {
		given:
		SeasonBaseResponse seasonBaseResponse = Mock()

		when:
		SeasonBaseResponse seasonBaseResponseOutput = season.search(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE, SEASON_NUMBER_FROM, SEASON_NUMBER_TO,
				NUMBER_OF_EPISODES_FROM, NUMBER_OF_EPISODES_TO)

		then:
		1 * seasonApiMock.seasonSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, TITLE, SEASON_NUMBER_FROM, SEASON_NUMBER_TO,
				NUMBER_OF_EPISODES_FROM, NUMBER_OF_EPISODES_TO) >> seasonBaseResponse
		0 * _
		seasonBaseResponse == seasonBaseResponseOutput
	}

}
