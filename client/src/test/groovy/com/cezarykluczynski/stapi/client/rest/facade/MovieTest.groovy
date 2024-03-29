package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.MovieApi
import com.cezarykluczynski.stapi.client.rest.model.MovieBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.MovieFullResponse
import com.cezarykluczynski.stapi.client.rest.model.MovieSearchCriteria
import com.cezarykluczynski.stapi.util.AbstractMovieTest

class MovieTest extends AbstractMovieTest {

	private MovieApi movieApiMock

	private Movie movie

	void setup() {
		movieApiMock = Mock()
		movie = new Movie(movieApiMock)
	}

	void "gets single entity"() {
		given:
		MovieFullResponse movieFullResponse = Mock()

		when:
		MovieFullResponse movieFullResponseOutput = movie.get(UID)

		then:
		1 * movieApiMock.v1GetMovie(UID) >> movieFullResponse
		0 * _
		movieFullResponse == movieFullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		MovieBaseResponse movieBaseResponse = Mock()
		MovieSearchCriteria movieSearchCriteria = new MovieSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				title: TITLE,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				usReleaseDateFrom: US_RELEASE_DATE_FROM,
				usReleaseDateTo: US_RELEASE_DATE_TO)
		movieSearchCriteria.sort = SORT

		when:
		MovieBaseResponse movieBaseResponseOutput = movie.search(movieSearchCriteria)

		then:
		1 * movieApiMock.v1SearchMovies(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, TITLE, STARDATE_FROM, STARDATE_TO, YEAR_FROM, YEAR_TO,
				US_RELEASE_DATE_FROM, US_RELEASE_DATE_TO) >> movieBaseResponse
		0 * _
		movieBaseResponse == movieBaseResponseOutput
	}

}
