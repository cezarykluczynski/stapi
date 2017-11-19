package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.EpisodeApi
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeFullResponse
import com.cezarykluczynski.stapi.util.AbstractEpisodeTest

class EpisodeTest extends AbstractEpisodeTest {

	private EpisodeApi episodeApiMock

	private Episode episode

	void setup() {
		episodeApiMock = Mock()
		episode = new Episode(episodeApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		EpisodeFullResponse episodeFullResponse = Mock()

		when:
		EpisodeFullResponse episodeFullResponseOutput = episode.get(UID)

		then:
		1 * episodeApiMock.episodeGet(UID, API_KEY) >> episodeFullResponse
		0 * _
		episodeFullResponse == episodeFullResponseOutput
	}

	void "searches entities"() {
		given:
		EpisodeBaseResponse episodeBaseResponse = Mock()

		when:
		EpisodeBaseResponse episodeBaseResponseOutput = episode.search(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE, SEASON_NUMBER_FROM, SEASON_NUMBER_TO,
				EPISODE_NUMBER_FROM, EPISODE_NUMBER_TO, PRODUCTION_SERIAL_NUMBER, FEATURE_LENGTH, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO,
				US_AIR_DATE_FROM, US_AIR_DATE_TO, FINAL_SCRIPT_DATE_FROM, FINAL_SCRIPT_DATE_TO)

		then:
		1 * episodeApiMock.episodeSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, TITLE, SEASON_NUMBER_FROM, SEASON_NUMBER_TO, EPISODE_NUMBER_FROM,
				EPISODE_NUMBER_TO, PRODUCTION_SERIAL_NUMBER, FEATURE_LENGTH, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO, US_AIR_DATE_FROM,
				US_AIR_DATE_TO, FINAL_SCRIPT_DATE_FROM, FINAL_SCRIPT_DATE_TO) >> episodeBaseResponse
		0 * _
		episodeBaseResponse == episodeBaseResponseOutput
	}

}
