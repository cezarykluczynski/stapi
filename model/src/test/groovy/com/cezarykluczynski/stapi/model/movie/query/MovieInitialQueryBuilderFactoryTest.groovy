package com.cezarykluczynski.stapi.model.movie.query

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.movie.entity.Movie_
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import java.time.LocalDate

class MovieInitialQueryBuilderFactoryTest extends Specification {

	private static final String UID = 'ABCD0123456789'
	private static final String TITLE = 'TITLE'
	private static final Float STARDATE_FROM = (Float) 3.3
	private static final Float STARDATE_TO = (Float) 4.6
	private static final LocalDate US_RELEASE_DATE_FROM = LocalDate.of(1968, 3, 4)
	private static final LocalDate US_RELEASE_DATE_TO = LocalDate.of(1968, 5, 6)
	private static final Integer YEAR_FROM = 2250
	private static final Integer YEAR_TO = 2370
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private MovieQueryBuilderFactory movieQueryBuilderMock

	private MovieInitialQueryBuilderFactory movieInitialQueryBuilderFactory

	private QueryBuilder<Movie> movieQueryBuilder

	private Pageable pageable

	private MovieRequestDTO movieRequestDTO

	private Movie movie

	private Page page

	void setup() {
		movieQueryBuilderMock = Mock()
		movieInitialQueryBuilderFactory = new MovieInitialQueryBuilderFactory(movieQueryBuilderMock)
		movieQueryBuilder = Mock()
		pageable = Mock()
		movieRequestDTO = Mock()
		page = Mock()
		movie = Mock()
	}

	void "initial query builder is built, then returned"() {
		when:
		QueryBuilder<Movie> movieQueryBuilderOutput = movieInitialQueryBuilderFactory
				.createInitialQueryBuilder(movieRequestDTO, pageable)

		then:
		1 * movieQueryBuilderMock.createQueryBuilder(pageable) >> movieQueryBuilder

		then: 'uid criteria is set'
		1 * movieRequestDTO.uid >> UID
		1 * movieQueryBuilder.equal(Movie_.uid, UID)

		then: 'string criteria are set'
		1 * movieRequestDTO.title >> TITLE
		1 * movieQueryBuilder.like(Movie_.title, TITLE)

		then: 'integer criteria are set'
		1 * movieRequestDTO.yearFrom >> YEAR_FROM
		1 * movieQueryBuilder.between(Movie_.yearFrom, YEAR_FROM, null)
		1 * movieRequestDTO.yearTo >> YEAR_TO
		1 * movieQueryBuilder.between(Movie_.yearTo, null, YEAR_TO)

		then: 'float criteria are set'
		1 * movieRequestDTO.stardateFrom >> STARDATE_FROM
		1 * movieQueryBuilder.between(Movie_.stardateFrom, STARDATE_FROM, null)
		1 * movieRequestDTO.stardateTo >> STARDATE_TO
		1 * movieQueryBuilder.between(Movie_.stardateTo, null, STARDATE_TO)

		then: 'date criteria are set'
		1 * movieRequestDTO.usReleaseDateFrom >> US_RELEASE_DATE_FROM
		1 * movieRequestDTO.usReleaseDateTo >> US_RELEASE_DATE_TO
		1 * movieQueryBuilder.between(Movie_.usReleaseDate, US_RELEASE_DATE_FROM, US_RELEASE_DATE_TO)

		then: 'sort is set'
		1 * movieRequestDTO.sort >> SORT
		1 * movieQueryBuilder.setSort(SORT)

		then: 'query builder is returned'
		movieQueryBuilderOutput == movieQueryBuilder

		then: 'no other interactions are expected'
		0 * _
	}

}
