package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.SeasonApi
import com.cezarykluczynski.stapi.client.rest.model.SeasonBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.SeasonFullResponse
import com.cezarykluczynski.stapi.client.rest.model.SeasonSearchCriteria
import com.cezarykluczynski.stapi.util.AbstractSeasonTest

class SeasonTest extends AbstractSeasonTest {

	private SeasonApi seasonApiMock

	private Season season

	void setup() {
		seasonApiMock = Mock()
		season = new Season(seasonApiMock)
	}

	void "gets single entity"() {
		given:
		SeasonFullResponse seasonFullResponse = Mock()

		when:
		SeasonFullResponse seasonFullResponseOutput = season.get(UID)

		then:
		1 * seasonApiMock.v1GetSeason(UID) >> seasonFullResponse
		0 * _
		seasonFullResponse == seasonFullResponseOutput
	}

	void "searches entities with criteria (V2)"() {
		given:
		SeasonBaseResponse seasonBaseResponse = Mock()
		SeasonSearchCriteria seasonSearchCriteria = new SeasonSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				title: TITLE,
				seasonNumberFrom: SEASON_NUMBER_FROM,
				seasonNumberTo: SEASON_NUMBER_TO,
				numberOfEpisodesFrom: NUMBER_OF_EPISODES_FROM,
				numberOfEpisodesTo: NUMBER_OF_EPISODES_TO)
		seasonSearchCriteria.sort = SORT

		when:
		SeasonBaseResponse seasonBaseResponseOutput = season.search(seasonSearchCriteria)

		then:
		1 * seasonApiMock.v1SearchSeasons(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, SEASON_NUMBER_FROM, SEASON_NUMBER_TO,
				NUMBER_OF_EPISODES_FROM, NUMBER_OF_EPISODES_TO) >> seasonBaseResponse
		0 * _
		seasonBaseResponse == seasonBaseResponseOutput
	}

}
