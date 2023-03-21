package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.api.dto.EpisodeSearchCriteria
import com.cezarykluczynski.stapi.client.rest.api.EpisodeApi
import com.cezarykluczynski.stapi.client.rest.model.EpisodeBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.EpisodeFullResponse
import com.cezarykluczynski.stapi.util.AbstractEpisodeTest

class EpisodeTest extends AbstractEpisodeTest {

	private EpisodeApi episodeApiMock

	private Episode episode

	void setup() {
		episodeApiMock = Mock()
		episode = new Episode(episodeApiMock)
	}

	void "gets single entity"() {
		given:
		EpisodeFullResponse episodeFullResponse = Mock()

		when:
		EpisodeFullResponse episodeFullResponseOutput = episode.get(UID)

		then:
		1 * episodeApiMock.v1Get(UID) >> episodeFullResponse
		0 * _
		episodeFullResponse == episodeFullResponseOutput
	}

	void "searches entities"() {
		given:
		EpisodeBaseResponse episodeBaseResponse = Mock()

		when:
		EpisodeBaseResponse episodeBaseResponseOutput = episode.search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, SEASON_NUMBER_FROM,
				SEASON_NUMBER_TO, EPISODE_NUMBER_FROM, EPISODE_NUMBER_TO, PRODUCTION_SERIAL_NUMBER, FEATURE_LENGTH, STARDATE_FROM, STARDATE_TO,
				YEAR_FROM, YEAR_TO, US_AIR_DATE_FROM, US_AIR_DATE_TO, FINAL_SCRIPT_DATE_FROM, FINAL_SCRIPT_DATE_TO)

		then:
		1 * episodeApiMock.v1Search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, SEASON_NUMBER_FROM, SEASON_NUMBER_TO,
				EPISODE_NUMBER_FROM, EPISODE_NUMBER_TO, PRODUCTION_SERIAL_NUMBER, FEATURE_LENGTH, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO,
				US_AIR_DATE_FROM, US_AIR_DATE_TO, FINAL_SCRIPT_DATE_FROM, FINAL_SCRIPT_DATE_TO) >> episodeBaseResponse
		0 * _
		episodeBaseResponse == episodeBaseResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		EpisodeBaseResponse episodeBaseResponse = Mock()
		EpisodeSearchCriteria episodeSearchCriteria = new EpisodeSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				title: TITLE,
				seasonNumberFrom: SEASON_NUMBER_FROM,
				seasonNumberTo: SEASON_NUMBER_TO,
				episodeNumberFrom: EPISODE_NUMBER_FROM,
				episodeNumberTo: EPISODE_NUMBER_TO,
				productionSerialNumber: PRODUCTION_SERIAL_NUMBER,
				featureLength: FEATURE_LENGTH,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				usAirDateFrom: US_AIR_DATE_FROM,
				usAirDateTo: US_AIR_DATE_TO,
				finalScriptDateFrom: FINAL_SCRIPT_DATE_FROM,
				finalScriptDateTo: FINAL_SCRIPT_DATE_TO)
		episodeSearchCriteria.sort.addAll(SORT)

		when:
		EpisodeBaseResponse episodeBaseResponseOutput = episode.search(episodeSearchCriteria)

		then:
		1 * episodeApiMock.v1Search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, SEASON_NUMBER_FROM, SEASON_NUMBER_TO,
				EPISODE_NUMBER_FROM, EPISODE_NUMBER_TO, PRODUCTION_SERIAL_NUMBER, FEATURE_LENGTH, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO,
				US_AIR_DATE_FROM, US_AIR_DATE_TO, FINAL_SCRIPT_DATE_FROM, FINAL_SCRIPT_DATE_TO) >> episodeBaseResponse
		0 * _
		episodeBaseResponse == episodeBaseResponseOutput
	}

}
