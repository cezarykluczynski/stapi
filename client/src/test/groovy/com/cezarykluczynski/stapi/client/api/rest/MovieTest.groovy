package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.MovieApi
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.MovieFullResponse
import com.cezarykluczynski.stapi.util.AbstractMovieTest

class MovieTest extends AbstractMovieTest {

	private MovieApi movieApiMock

	private Movie movie

	void setup() {
		movieApiMock = Mock()
		movie = new Movie(movieApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		MovieFullResponse movieFullResponse = Mock()

		when:
		MovieFullResponse movieFullResponseOutput = movie.get(UID)

		then:
		1 * movieApiMock.movieGet(UID, API_KEY) >> movieFullResponse
		0 * _
		movieFullResponse == movieFullResponseOutput
	}

	void "searches entities"() {
		given:
		MovieBaseResponse movieBaseResponse = Mock()

		when:
		MovieBaseResponse movieBaseResponseOutput = movie.search(PAGE_NUMBER, PAGE_SIZE, SORT, TITLE, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO,
				US_RELEASE_DATE_FROM, US_RELEASE_DATE_TO)

		then:
		1 * movieApiMock.movieSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, TITLE, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO,
				US_RELEASE_DATE_FROM, US_RELEASE_DATE_TO) >> movieBaseResponse
		0 * _
		movieBaseResponse == movieBaseResponseOutput
	}

}
