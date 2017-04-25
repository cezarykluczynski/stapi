package com.cezarykluczynski.stapi.model.staff.repository

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.movie.entity.Movie_
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.model.staff.entity.Staff_
import com.cezarykluczynski.stapi.model.staff.query.StaffInitialQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractRealWorldPersonTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class StaffRepositoryImplTest extends AbstractRealWorldPersonTest {

	private StaffInitialQueryBuilderFactory staffInitialQueryBuilderFactory

	private StaffRepositoryImpl staffRepositoryImpl

	private QueryBuilder<Staff> staffQueryBuilder

	private QueryBuilder<Staff> staffMoviesQueryBuilder

	private Pageable pageable

	private StaffRequestDTO staffRequestDTO

	private Staff staff

	private Staff moviesStaff

	private Page page

	private Set<Movie> writtenMoviesSet

	private Set<Movie> screenplayAuthoredMoviesSet

	private Set<Movie> storyAuthoredMovies

	private Set<Movie> directedMovies

	private Set<Movie> producedMovies

	private Set<Movie> moviesSet

	void setup() {
		staffInitialQueryBuilderFactory = Mock()
		staffRepositoryImpl = new StaffRepositoryImpl(staffInitialQueryBuilderFactory)
		staffQueryBuilder = Mock()
		staffMoviesQueryBuilder = Mock()
		pageable = Mock()
		staffRequestDTO = Mock()
		staff = Mock()
		moviesStaff = Mock()
		page = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = staffRepositoryImpl.findMatching(staffRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * staffInitialQueryBuilderFactory.createInitialQueryBuilder(staffRequestDTO, pageable) >> staffQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * staffRequestDTO.uid >> UID

		then: 'episodes fetch is performed'
		1 * staffQueryBuilder.fetch(Staff_.writtenEpisodes)
		1 * staffQueryBuilder.fetch(Staff_.teleplayAuthoredEpisodes)
		1 * staffQueryBuilder.fetch(Staff_.storyAuthoredEpisodes)
		1 * staffQueryBuilder.fetch(Staff_.directedEpisodes)
		1 * staffQueryBuilder.fetch(Staff_.episodes)

		then: 'page is retrieved'
		1 * staffQueryBuilder.findPage() >> page
		1 * page.content >> Lists.newArrayList(staff)

		then: 'another criteria builder is retrieved for performers'
		1 * staffInitialQueryBuilderFactory.createInitialQueryBuilder(staffRequestDTO, pageable) >> staffMoviesQueryBuilder

		then: 'performers fetch is performed'
		1 * staffMoviesQueryBuilder.fetch(Staff_.writtenMovies)
		1 * staffMoviesQueryBuilder.fetch(Staff_.screenplayAuthoredMovies)
		1 * staffMoviesQueryBuilder.fetch(Staff_.storyAuthoredMovies)
		1 * staffMoviesQueryBuilder.fetch(Staff_.directedMovies)
		1 * staffMoviesQueryBuilder.fetch(Staff_.producedMovies)
		1 * staffMoviesQueryBuilder.fetch(Staff_.movies)
		1 * staffMoviesQueryBuilder.fetch(Staff_.movies, Movie_.mainDirector)

		then: 'staff list is retrieved'
		1 * staffMoviesQueryBuilder.findAll() >> Lists.newArrayList(moviesStaff)

		then: 'movies from movies staff are set to movie'
		1 * moviesStaff.writtenMovies >> writtenMoviesSet
		1 * staff.setWrittenMovies(writtenMoviesSet)
		1 * moviesStaff.screenplayAuthoredMovies >> screenplayAuthoredMoviesSet
		1 * staff.setScreenplayAuthoredMovies(screenplayAuthoredMoviesSet)
		1 * moviesStaff.storyAuthoredMovies >> storyAuthoredMovies
		1 * staff.setStoryAuthoredMovies(storyAuthoredMovies)
		1 * moviesStaff.writtenMovies >> writtenMoviesSet
		1 * staff.setDirectedMovies(directedMovies)
		1 * moviesStaff.producedMovies >> producedMovies
		1 * staff.setProducedMovies(producedMovies)
		1 * moviesStaff.movies >> moviesSet
		1 * staff.setMovies(moviesSet)

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "query is built and performed without results from additional queries"() {
		when:
		Page pageOutput = staffRepositoryImpl.findMatching(staffRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * staffInitialQueryBuilderFactory.createInitialQueryBuilder(staffRequestDTO, pageable) >> staffQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * staffRequestDTO.uid >> UID

		then: 'episodes fetch is performed'
		1 * staffQueryBuilder.fetch(Staff_.writtenEpisodes)
		1 * staffQueryBuilder.fetch(Staff_.teleplayAuthoredEpisodes)
		1 * staffQueryBuilder.fetch(Staff_.storyAuthoredEpisodes)
		1 * staffQueryBuilder.fetch(Staff_.directedEpisodes)
		1 * staffQueryBuilder.fetch(Staff_.episodes)

		then: 'page is retrieved'
		1 * staffQueryBuilder.findPage() >> page
		1 * page.content >> Lists.newArrayList(staff)

		then: 'another criteria builder is retrieved for performers'
		1 * staffInitialQueryBuilderFactory.createInitialQueryBuilder(staffRequestDTO, pageable) >> staffMoviesQueryBuilder

		then: 'performers fetch is performed'
		1 * staffMoviesQueryBuilder.fetch(Staff_.writtenMovies)
		1 * staffMoviesQueryBuilder.fetch(Staff_.screenplayAuthoredMovies)
		1 * staffMoviesQueryBuilder.fetch(Staff_.storyAuthoredMovies)
		1 * staffMoviesQueryBuilder.fetch(Staff_.directedMovies)
		1 * staffMoviesQueryBuilder.fetch(Staff_.producedMovies)
		1 * staffMoviesQueryBuilder.fetch(Staff_.movies)
		1 * staffMoviesQueryBuilder.fetch(Staff_.movies, Movie_.mainDirector)

		then: 'empty staff list is retrieved'
		1 * staffMoviesQueryBuilder.findAll() >> Lists.newArrayList()

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "empty page is returned"() {
		when:
		Page pageOutput = staffRepositoryImpl.findMatching(staffRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * staffInitialQueryBuilderFactory.createInitialQueryBuilder(staffRequestDTO, pageable) >> staffQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * staffRequestDTO.uid >> UID

		then: 'episodes fetch is performed'
		1 * staffQueryBuilder.fetch(Staff_.writtenEpisodes)
		1 * staffQueryBuilder.fetch(Staff_.teleplayAuthoredEpisodes)
		1 * staffQueryBuilder.fetch(Staff_.storyAuthoredEpisodes)
		1 * staffQueryBuilder.fetch(Staff_.directedEpisodes)
		1 * staffQueryBuilder.fetch(Staff_.episodes)

		then: 'page is retrieved'
		1 * staffQueryBuilder.findPage() >> page
		1 * page.content >> Lists.newArrayList()

		then: 'page is returned'
		pageOutput == page
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = staffRepositoryImpl.findMatching(staffRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * staffInitialQueryBuilderFactory.createInitialQueryBuilder(staffRequestDTO, pageable) >> staffQueryBuilder

		then: 'uid criteria is set to null'
		1 * staffRequestDTO.uid >> null

		then: 'page is searched for and returned'
		1 * staffQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(staff)
		1 * staff.setWrittenEpisodes(Sets.newHashSet())
		1 * staff.setTeleplayAuthoredEpisodes(Sets.newHashSet())
		1 * staff.setStoryAuthoredEpisodes(Sets.newHashSet())
		1 * staff.setDirectedEpisodes(Sets.newHashSet())
		1 * staff.setEpisodes(Sets.newHashSet())
		1 * staff.setWrittenMovies(Sets.newHashSet())
		1 * staff.setScreenplayAuthoredMovies(Sets.newHashSet())
		1 * staff.setWrittenEpisodes(Sets.newHashSet())
		1 * staff.setStoryAuthoredMovies(Sets.newHashSet())
		1 * staff.setDirectedMovies(Sets.newHashSet())
		1 * staff.setProducedMovies(Sets.newHashSet())
		1 * staff.setMovies(Sets.newHashSet())
		pageOutput == page
	}

}
