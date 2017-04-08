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

	void setup() {
		movieWritersLinkingWorkerMock = Mock()
		movieScreenplayAuthorsLinkingWorkerMock = Mock()
		movieStoryAuthorsLinkingWorkerMock = Mock()
		movieDirectorsLinkingWorkerMock = Mock()
		movieProducersLinkingWorkerMock = Mock()
		movieStaffLinkingWorkerMock = Mock()
		movieStaffLinkingWorkerComposite = new MovieStaffLinkingWorkerComposite(movieWritersLinkingWorkerMock,
				movieScreenplayAuthorsLinkingWorkerMock, movieStoryAuthorsLinkingWorkerMock, movieDirectorsLinkingWorkerMock,
				movieProducersLinkingWorkerMock, movieStaffLinkingWorkerMock)
	}

	void "passes the right sets of staff to particular workers"() {
		given:
		MovieLinkedTitlesDTO movieLinkedTitlesDTO = Mock()
		Set<List<String>> writers = Mock()
		Set<List<String>> screenplayAuthors = Mock()
		Set<List<String>> storyAuthors = Mock()
		Set<List<String>> directors = Mock()
		Set<List<String>> producers = Mock()
		Set<List<String>> staff = Mock()
		Movie movie = Mock()

		when:
		movieStaffLinkingWorkerComposite.link(movieLinkedTitlesDTO, movie)

		then: 'linking workers are used to process particulars sets of link lists'
		1 * movieLinkedTitlesDTO.writers >> writers
		1 * movieWritersLinkingWorkerMock.link(writers, movie)
		1 * movieLinkedTitlesDTO.screenplayAuthors >> screenplayAuthors
		1 * movieScreenplayAuthorsLinkingWorkerMock.link(screenplayAuthors, movie)
		1 * movieLinkedTitlesDTO.storyAuthors >> storyAuthors
		1 * movieStoryAuthorsLinkingWorkerMock.link(storyAuthors, movie)
		1 * movieLinkedTitlesDTO.directors >> directors
		1 * movieDirectorsLinkingWorkerMock.link(directors, movie)
		1 * movieLinkedTitlesDTO.producers >> producers
		1 * movieProducersLinkingWorkerMock.link(producers, movie)
		1 * movieLinkedTitlesDTO.staff >> staff
		1 * movieStaffLinkingWorkerMock.link(staff, movie)
		0 * _
	}

}
