package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.ComicsApi
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ComicsFullResponse
import com.cezarykluczynski.stapi.util.AbstractComicsTest

class ComicsTest extends AbstractComicsTest {

	private ComicsApi comicsApiMock

	private Comics comics

	void setup() {
		comicsApiMock = Mock()
		comics = new Comics(comicsApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		ComicsFullResponse comicsFullResponse = Mock()

		when:
		ComicsFullResponse comicsFullResponseOutput = comics.get(UID)

		then:
		1 * comicsApiMock.comicsGet(UID, API_KEY) >> comicsFullResponse
		0 * _
		comicsFullResponse == comicsFullResponseOutput
	}

	void "searches entities"() {
		given:
		ComicsBaseResponse comicsBaseResponse = Mock()

		when:
		ComicsBaseResponse comicsBaseResponseOutput = comics.search(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO, PHOTONOVEL, ADAPTATION)

		then:
		1 * comicsApiMock.comicsSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO, NUMBER_OF_PAGES_FROM,
				NUMBER_OF_PAGES_TO, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO, PHOTONOVEL, ADAPTATION) >> comicsBaseResponse
		0 * _
		comicsBaseResponse == comicsBaseResponseOutput
	}

}
