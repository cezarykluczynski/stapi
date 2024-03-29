package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.ComicsApi
import com.cezarykluczynski.stapi.client.rest.model.ComicsBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.ComicsFullResponse
import com.cezarykluczynski.stapi.client.rest.model.ComicsSearchCriteria
import com.cezarykluczynski.stapi.util.AbstractComicsTest

class ComicsTest extends AbstractComicsTest {

	private ComicsApi comicsApiMock

	private Comics comics

	void setup() {
		comicsApiMock = Mock()
		comics = new Comics(comicsApiMock)
	}

	void "gets single entity"() {
		given:
		ComicsFullResponse comicsFullResponse = Mock()

		when:
		ComicsFullResponse comicsFullResponseOutput = comics.get(UID)

		then:
		1 * comicsApiMock.v1GetComics(UID) >> comicsFullResponse
		0 * _
		comicsFullResponse == comicsFullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		ComicsBaseResponse comicsBaseResponse = Mock()
		ComicsSearchCriteria comicsSearchCriteria = new ComicsSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				title: TITLE,
				publishedYearFrom: PUBLISHED_YEAR_FROM,
				publishedYearTo: PUBLISHED_YEAR_TO,
				numberOfPagesFrom: NUMBER_OF_PAGES_FROM,
				numberOfPagesTo: NUMBER_OF_PAGES_TO,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				photonovel: PHOTONOVEL,
				adaptation: ADAPTATION)
		comicsSearchCriteria.sort = SORT

		when:
		ComicsBaseResponse comicsBaseResponseOutput = comics.search(comicsSearchCriteria)

		then:
		1 * comicsApiMock.v1SearchComics(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, PUBLISHED_YEAR_FROM, PUBLISHED_YEAR_TO,
				NUMBER_OF_PAGES_FROM, NUMBER_OF_PAGES_TO, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO, PHOTONOVEL, ADAPTATION) >>
				comicsBaseResponse
		0 * _
		comicsBaseResponse == comicsBaseResponseOutput
	}

}
