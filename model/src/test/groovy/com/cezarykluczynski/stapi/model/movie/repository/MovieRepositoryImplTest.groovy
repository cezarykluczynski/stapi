package com.cezarykluczynski.stapi.model.movie.repository

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.movie.dto.MovieRequestDTO
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.movie.entity.Movie_
import com.cezarykluczynski.stapi.model.movie.query.MovieInitialQueryBuilderFactory
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class MovieRepositoryImplTest extends Specification {

	private static final String UID = 'ABCD0123456789'

	private MovieInitialQueryBuilderFactory movieInitialQueryBuilderFactoryMock

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
		movieInitialQueryBuilderFactoryMock = Mock()
		movieRepositoryImpl = new MovieRepositoryImpl(movieInitialQueryBuilderFactoryMock)
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
		1 * movieInitialQueryBuilderFactoryMock.createInitialQueryBuilder(movieRequestDTO, pageable) >> movieQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * movieRequestDTO.uid >> UID

		then: 'main director is fetched'
		1 * movieQueryBuilder.fetch(Movie_.mainDirector)

		then: 'staff fetch is performed'
		1 * movieQueryBuilder.fetch(Movie_.writers)
		1 * movieQueryBuilder.fetch(Movie_.screenplayAuthors)
		1 * movieQueryBuilder.fetch(Movie_.storyAuthors)
		1 * movieQueryBuilder.fetch(Movie_.directors)
		1 * movieQueryBuilder.fetch(Movie_.producers)
		1 * movieQueryBuilder.fetch(Movie_.staff)

		then: 'page is retrieved'
		1 * movieQueryBuilder.findPage() >> page
		1 * page.content >> Lists.newArrayList(movie)

		then: 'another criteria builder is retrieved for performers'
		1 * movieInitialQueryBuilderFactoryMock.createInitialQueryBuilder(movieRequestDTO, pageable) >> moviePerformersQueryBuilder

		then: 'performers fetch is performed'
		1 * moviePerformersQueryBuilder.fetch(Movie_.performers)
		1 * moviePerformersQueryBuilder.fetch(Movie_.stuntPerformers)
		1 * moviePerformersQueryBuilder.fetch(Movie_.standInPerformers)

		then: 'result list is retrieved'
		1 * moviePerformersQueryBuilder.findAll() >> Lists.newArrayList(performersMovie)

		then: 'performers from performers movie are set to movie'
		1 * performersMovie.performers >> performersSet
		1 * movie.setPerformers(performersSet)
		1 * performersMovie.stuntPerformers >> stuntPerformersSet
		1 * movie.setStuntPerformers(stuntPerformersSet)
		1 * performersMovie.standInPerformers >> standInPerformersSet
		1 * movie.setStandInPerformers(standInPerformersSet)

		then: 'another criteria builder is retrieved for characters'
		1 * movieInitialQueryBuilderFactoryMock.createInitialQueryBuilder(movieRequestDTO, pageable) >> movieCharactersQueryBuilder

		then: 'characters fetch is performed'
		1 * movieCharactersQueryBuilder.fetch(Movie_.characters)

		then: 'result list is retrieved'
		1 * movieCharactersQueryBuilder.findAll() >> Lists.newArrayList(charactersMovie)

		then: 'performers from performers movie are set to movie'
		1 * charactersMovie.characters >> charactersSet
		1 * movie.setCharacters(charactersSet)

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "query is built and performed without results from additional queries"() {
		when:
		Page pageOutput = movieRepositoryImpl.findMatching(movieRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * movieInitialQueryBuilderFactoryMock.createInitialQueryBuilder(movieRequestDTO, pageable) >> movieQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * movieRequestDTO.uid >> UID

		then: 'main director is fetched'
		1 * movieQueryBuilder.fetch(Movie_.mainDirector)

		then: 'staff fetch is performed'
		1 * movieQueryBuilder.fetch(Movie_.writers)
		1 * movieQueryBuilder.fetch(Movie_.screenplayAuthors)
		1 * movieQueryBuilder.fetch(Movie_.storyAuthors)
		1 * movieQueryBuilder.fetch(Movie_.directors)
		1 * movieQueryBuilder.fetch(Movie_.producers)
		1 * movieQueryBuilder.fetch(Movie_.staff)

		then: 'page is retrieved'
		1 * movieQueryBuilder.findPage() >> page
		1 * page.content >> Lists.newArrayList(movie)

		then: 'another criteria builder is retrieved for performers'
		1 * movieInitialQueryBuilderFactoryMock.createInitialQueryBuilder(movieRequestDTO, pageable) >> moviePerformersQueryBuilder

		then: 'performers fetch is performed'
		1 * moviePerformersQueryBuilder.fetch(Movie_.performers)
		1 * moviePerformersQueryBuilder.fetch(Movie_.stuntPerformers)
		1 * moviePerformersQueryBuilder.fetch(Movie_.standInPerformers)

		then: 'empty performers list is retrieved'
		1 * moviePerformersQueryBuilder.findAll() >> Lists.newArrayList()

		then: 'another criteria builder is retrieved for characters'
		1 * movieInitialQueryBuilderFactoryMock.createInitialQueryBuilder(movieRequestDTO, pageable) >> movieCharactersQueryBuilder

		then: 'characters fetch is performed'
		1 * movieCharactersQueryBuilder.fetch(Movie_.characters)

		then: 'empty characters list is retrieved'
		1 * movieCharactersQueryBuilder.findAll() >> Lists.newArrayList()

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "empty page is returned"() {
		when:
		Page pageOutput = movieRepositoryImpl.findMatching(movieRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * movieInitialQueryBuilderFactoryMock.createInitialQueryBuilder(movieRequestDTO, pageable) >> movieQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * movieRequestDTO.uid >> UID

		then: 'main director is fetched'
		1 * movieQueryBuilder.fetch(Movie_.mainDirector)

		then: 'staff fetch is performed'
		1 * movieQueryBuilder.fetch(Movie_.writers)
		1 * movieQueryBuilder.fetch(Movie_.screenplayAuthors)
		1 * movieQueryBuilder.fetch(Movie_.storyAuthors)
		1 * movieQueryBuilder.fetch(Movie_.directors)
		1 * movieQueryBuilder.fetch(Movie_.producers)
		1 * movieQueryBuilder.fetch(Movie_.staff)

		then: 'page is retrieved'
		1 * movieQueryBuilder.findPage() >> page
		1 * page.content >> Lists.newArrayList()

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = movieRepositoryImpl.findMatching(movieRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * movieInitialQueryBuilderFactoryMock.createInitialQueryBuilder(movieRequestDTO, pageable) >> movieQueryBuilder

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
