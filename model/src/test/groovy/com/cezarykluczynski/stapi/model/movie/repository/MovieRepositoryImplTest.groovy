package com.cezarykluczynski.stapi.model.movie.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.movie.entity.Movie_
import com.cezarykluczynski.stapi.model.movie.query.MovieQueryBuilderFactory
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

import java.time.LocalDate

class MovieRepositoryImplTest extends Specification {

	private static final String GUID = 'ABCD0123456789'
	private static final String TITLE = 'TITLE'
	private static final Float STARDATE_FROM = (Float) 3.3
	private static final Float STARDATE_TO = (Float) 4.6
	private static final LocalDate US_RELEASE_DATE_FROM = LocalDate.of(1968, 3, 4)
	private static final LocalDate US_RELEASE_DATE_TO = LocalDate.of(1968, 5, 6)
	private static final Integer YEAR_FROM = 2250
	private static final Integer YEAR_TO = 2370
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private MovieQueryBuilderFactory movieQueryBuilderMock

	private MovieRepositoryImpl movieRepositoryImpl

	private QueryBuilder<Movie> movieQueryBuilder

	private Pageable pageable

	private MovieRequestDTO movieRequestDTO

	private Movie movie

	private Page page

	def setup() {
		movieQueryBuilderMock = Mock(MovieQueryBuilderFactory)
		movieRepositoryImpl = new MovieRepositoryImpl(movieQueryBuilderMock)
		movieQueryBuilder = Mock(QueryBuilder)
		pageable = Mock(Pageable)
		movieRequestDTO = Mock(MovieRequestDTO)
		page = Mock(Page)
		movie = Mock(Movie)
	}

	def "query is built and performed"() {
		when:
		Page pageOutput = movieRepositoryImpl.findMatching(movieRequestDTO, pageable)

		then:
		1 * movieQueryBuilderMock.createQueryBuilder(pageable) >> movieQueryBuilder

		then: 'guid criteria is set'
		1 * movieRequestDTO.getGuid() >> GUID
		1 * movieQueryBuilder.equal(Movie_.guid, GUID)

		then: 'string criteria are set'
		1 * movieRequestDTO.getTitle() >> TITLE
		1 * movieQueryBuilder.like(Movie_.title, TITLE)

		then: 'integer criteria are set'
		1 * movieRequestDTO.getYearFrom() >> YEAR_FROM
		1 * movieRequestDTO.getYearTo() >> YEAR_TO
		1 * movieQueryBuilder.between(Movie_.yearFrom, YEAR_FROM, null)
		1 * movieQueryBuilder.between(Movie_.yearTo, null, YEAR_TO)

		then: 'float criteria are set'
		1 * movieRequestDTO.getStardateFrom() >> STARDATE_FROM
		1 * movieRequestDTO.getStardateTo() >> STARDATE_TO
		1 * movieQueryBuilder.between(Movie_.stardateFrom, STARDATE_FROM, null)
		1 * movieQueryBuilder.between(Movie_.stardateTo, null, STARDATE_TO)

		then: 'date criteria are set'
		1 * movieRequestDTO.getUsReleaseDateFrom() >> US_RELEASE_DATE_FROM
		1 * movieRequestDTO.getUsReleaseDateTo() >> US_RELEASE_DATE_TO
		1 * movieQueryBuilder.between(Movie_.usReleaseDate, US_RELEASE_DATE_FROM, US_RELEASE_DATE_TO)

		then: 'sort is set'
		1 * movieRequestDTO.getSort() >> SORT
		1 * movieQueryBuilder.setSort(SORT)

		then: 'fetch is performed with true flag'
		1 * movieQueryBuilder.fetch(Movie_.writers, true)
		1 * movieQueryBuilder.fetch(Movie_.screenplayAuthors, true)
		1 * movieQueryBuilder.fetch(Movie_.storyAuthors, true)
		1 * movieQueryBuilder.fetch(Movie_.directors, true)
		1 * movieQueryBuilder.fetch(Movie_.producers, true)
		1 * movieQueryBuilder.fetch(Movie_.staff, true)
		1 * movieQueryBuilder.fetch(Movie_.performers, true)
		1 * movieQueryBuilder.fetch(Movie_.stuntPerformers, true)
		1 * movieQueryBuilder.fetch(Movie_.standInPerformers, true)
		1 * movieQueryBuilder.fetch(Movie_.characters, true)

		then: 'page is searched for and returned'
		1 * movieQueryBuilder.findPage() >> page
		0 * page.getContent()
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	def "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = movieRepositoryImpl.findMatching(movieRequestDTO, pageable)

		then:
		1 * movieQueryBuilderMock.createQueryBuilder(pageable) >> movieQueryBuilder

		then: 'guid criteria is set to null'
		1 * movieRequestDTO.getGuid() >> null

		then: 'fetch is performed with false flag'
		1 * movieQueryBuilder.fetch(Movie_.writers, false)
		1 * movieQueryBuilder.fetch(Movie_.screenplayAuthors, false)
		1 * movieQueryBuilder.fetch(Movie_.storyAuthors, false)
		1 * movieQueryBuilder.fetch(Movie_.directors, false)
		1 * movieQueryBuilder.fetch(Movie_.producers, false)
		1 * movieQueryBuilder.fetch(Movie_.staff, false)
		1 * movieQueryBuilder.fetch(Movie_.performers, false)
		1 * movieQueryBuilder.fetch(Movie_.stuntPerformers, false)
		1 * movieQueryBuilder.fetch(Movie_.standInPerformers, false)
		1 * movieQueryBuilder.fetch(Movie_.characters, false)

		then: 'page is searched for and returned'
		1 * movieQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.getContent() >> Lists.newArrayList(movie)
		1 * movie.setWriters(Sets.newHashSet())
		1 * movie.setScreenplayAuthors(Sets.newHashSet())
		1 * movie.setStoryAuthors(Sets.newHashSet())
		1 * movie.setDirectors(Sets.newHashSet())
		1 * movie.setProducers(Sets.newHashSet())
		1 * movie.setStaff(Sets.newHashSet())
		1 * movie.setPerformers(Sets.newHashSet())
		1 * movie.setStuntPerformers(Sets.newHashSet())
		1 * movie.setStandInPerformers(Sets.newHashSet())
		1 * movie.setCharacters(Sets.newConcurrentHashSet())
		pageOutput == page
	}

}
