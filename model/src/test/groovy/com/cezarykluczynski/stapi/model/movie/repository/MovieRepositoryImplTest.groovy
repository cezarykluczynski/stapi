package com.cezarykluczynski.stapi.model.movie.repository

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.movie.entity.Movie_
import com.cezarykluczynski.stapi.model.movie.query.MovieQueryBuilderFactory
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.util.AbstractMovieTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class MovieRepositoryImplTest extends AbstractMovieTest {

	private static final RequestSortDTO SORT = new RequestSortDTO()

	private MovieQueryBuilderFactory movieQueryBuilderFactory

	private MovieRepositoryImpl movieRepositoryImpl

	private QueryBuilder<Movie> movieQueryBuilder

	private QueryBuilder<Movie> moviePerformersQueryBuilder

	private QueryBuilder<Movie> movieCharactersQueryBuilder

	private Pageable pageable

	private MovieRequestDTO movieRequestDTO

	private Movie movie

	private Movie performersMovie

	private Movie charactersMovie

	private Page page

	private Page performersPage

	private Page charactersPage

	private Set<Performer> performersSet

	private Set<Performer> stuntPerformersSet

	private Set<Performer> standInPerformersSet

	private Set<Character> charactersSet

	void setup() {
		movieQueryBuilderFactory = Mock()
		movieRepositoryImpl = new MovieRepositoryImpl(movieQueryBuilderFactory)
		movieQueryBuilder = Mock()
		moviePerformersQueryBuilder = Mock()
		movieCharactersQueryBuilder = Mock()
		pageable = Mock()
		movieRequestDTO = Mock()
		page = Mock()
		performersPage = Mock()
		charactersPage = Mock()
		movie = Mock()
		performersMovie = Mock()
		charactersMovie = Mock()
		performersSet = Mock()
		stuntPerformersSet = Mock()
		standInPerformersSet = Mock()
		charactersSet = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = movieRepositoryImpl.findMatching(movieRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * movieQueryBuilderFactory.createQueryBuilder(pageable) >> movieQueryBuilder

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

		then: 'fetch is performed'
		1 * movieQueryBuilder.fetch(Movie_.mainDirector)
		1 * movieQueryBuilder.divideQueries()
		1 * movieQueryBuilder.fetch(Movie_.writers, true)
		1 * movieQueryBuilder.divideQueries()
		1 * movieQueryBuilder.fetch(Movie_.screenplayAuthors, true)
		1 * movieQueryBuilder.divideQueries()
		1 * movieQueryBuilder.fetch(Movie_.storyAuthors, true)
		1 * movieQueryBuilder.divideQueries()
		1 * movieQueryBuilder.fetch(Movie_.directors, true)
		1 * movieQueryBuilder.divideQueries()
		1 * movieQueryBuilder.fetch(Movie_.producers, true)
		1 * movieQueryBuilder.divideQueries()
		1 * movieQueryBuilder.fetch(Movie_.staff, true)
		1 * movieQueryBuilder.divideQueries()
		1 * movieQueryBuilder.fetch(Movie_.performers, true)
		1 * movieQueryBuilder.divideQueries()
		1 * movieQueryBuilder.fetch(Movie_.stuntPerformers, true)
		1 * movieQueryBuilder.divideQueries()
		1 * movieQueryBuilder.fetch(Movie_.standInPerformers, true)
		1 * movieQueryBuilder.divideQueries()
		1 * movieQueryBuilder.fetch(Movie_.characters, true)

		then: 'page is retrieved'
		1 * movieQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = movieRepositoryImpl.findMatching(movieRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * movieQueryBuilderFactory.createQueryBuilder(pageable) >> movieQueryBuilder

		then: 'uid criteria is set to null'
		1 * movieRequestDTO.uid >> null

		then: 'page is searched for and returned'
		1 * movieQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(movie)
		1 * movie.setWriters(Sets.newHashSet())
		1 * movie.setScreenplayAuthors(Sets.newHashSet())
		1 * movie.setStoryAuthors(Sets.newHashSet())
		1 * movie.setDirectors(Sets.newHashSet())
		1 * movie.setProducers(Sets.newHashSet())
		1 * movie.setStaff(Sets.newHashSet())
		1 * movie.setPerformers(Sets.newHashSet())
		1 * movie.setStuntPerformers(Sets.newHashSet())
		1 * movie.setStandInPerformers(Sets.newHashSet())
		1 * movie.setCharacters(Sets.newHashSet())
		pageOutput == page
	}

}
