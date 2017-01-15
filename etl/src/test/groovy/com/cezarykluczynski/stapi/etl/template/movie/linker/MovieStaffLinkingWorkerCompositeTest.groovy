package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.etl.movie.creation.dto.MovieLinkedTitlesDTO
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import spock.lang.Specification

class MovieStaffLinkingWorkerCompositeTest extends Specification {

	private MovieWritersLinkingWorker movieWritersLinkingWorkerMock

	private MovieScreenplayAuthorsLinkingWorker movieScreenplayAuthorsLinkingWorkerMock

	private MovieStoryAuthorsLinkingWorker movieStoryAuthorsLinkingWorkerMock

	private MovieDirectorsLinkingWorker movieDirectorsLinkingWorkerMock

	private MovieProducersLinkingWorker movieProducersLinkingWorkerMock

	private MovieStaffLinkingWorker movieStaffLinkingWorkerMock

	private MovieStaffLinkingWorkerComposite movieStaffLinkingWorkerComposite

	def setup() {
		movieWritersLinkingWorkerMock = Mock(MovieWritersLinkingWorker)
		movieScreenplayAuthorsLinkingWorkerMock = Mock(MovieScreenplayAuthorsLinkingWorker)
		movieStoryAuthorsLinkingWorkerMock = Mock(MovieStoryAuthorsLinkingWorker)
		movieDirectorsLinkingWorkerMock = Mock(MovieDirectorsLinkingWorker)
		movieProducersLinkingWorkerMock = Mock(MovieProducersLinkingWorker)
		movieStaffLinkingWorkerMock = Mock(MovieStaffLinkingWorker)
		movieStaffLinkingWorkerComposite = new MovieStaffLinkingWorkerComposite(movieWritersLinkingWorkerMock,
				movieScreenplayAuthorsLinkingWorkerMock, movieStoryAuthorsLinkingWorkerMock, movieDirectorsLinkingWorkerMock,
				movieProducersLinkingWorkerMock, movieStaffLinkingWorkerMock)
	}

	def "passes the right sets of staff to particular workers"() {
		given:
		MovieLinkedTitlesDTO movieLinkedTitlesDTO = Mock(MovieLinkedTitlesDTO)
		Set<List<String>> writers = Mock(Set)
		Set<List<String>> screenplayAuthors = Mock(Set)
		Set<List<String>> storyAuthors = Mock(Set)
		Set<List<String>> directors = Mock(Set)
		Set<List<String>> producers = Mock(Set)
		Set<List<String>> staff = Mock(Set)
		Movie movie = Mock(Movie)

		when:
		movieStaffLinkingWorkerComposite.link(movieLinkedTitlesDTO, movie)

		then: 'linking workers are used to process particulars sets of link lists'
		1 * movieLinkedTitlesDTO.getWriters() >> writers
		1 * movieWritersLinkingWorkerMock.link(writers, movie)
		1 * movieLinkedTitlesDTO.getScreenplayAuthors() >> screenplayAuthors
		1 * movieScreenplayAuthorsLinkingWorkerMock.link(screenplayAuthors, movie)
		1 * movieLinkedTitlesDTO.getStoryAuthors() >> storyAuthors
		1 * movieStoryAuthorsLinkingWorkerMock.link(storyAuthors, movie)
		1 * movieLinkedTitlesDTO.getDirectors() >> directors
		1 * movieDirectorsLinkingWorkerMock.link(directors, movie)
		1 * movieLinkedTitlesDTO.getProducers() >> producers
		1 * movieProducersLinkingWorkerMock.link(producers, movie)
		1 * movieLinkedTitlesDTO.getStaff() >> staff
		1 * movieStaffLinkingWorkerMock.link(staff, movie)
		0 * _
	}

}
