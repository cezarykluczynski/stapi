package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.etl.movie.creation.dto.MovieLinkedTitlesDTO
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import spock.lang.Specification

class MoviePerformersLinkingWorkerCompositeTest extends Specification {

	private MoviePerformersCharactersLinkingWorker moviePerformersCharacterLinkingWorkerMock

	private MovieStuntPerformersLinkingWorker movieStuntPerformersLinkingWorkerMock

	private MovieStandInPerformersLinkingWorker movieStandInPerformersLinkingWorkerMock

	private MoviePerformersLinkingWorkerComposite moviePerformersLinkingWorkerComposite

	def setup() {
		moviePerformersCharacterLinkingWorkerMock = Mock(MoviePerformersCharactersLinkingWorker)
		movieStuntPerformersLinkingWorkerMock = Mock(MovieStuntPerformersLinkingWorker)
		movieStandInPerformersLinkingWorkerMock = Mock(MovieStandInPerformersLinkingWorker)
		moviePerformersLinkingWorkerComposite = new MoviePerformersLinkingWorkerComposite(moviePerformersCharacterLinkingWorkerMock,
				movieStuntPerformersLinkingWorkerMock, movieStandInPerformersLinkingWorkerMock)
	}

	def "passes the right sets of performers to particular workers"() {
		given:
		MovieLinkedTitlesDTO movieLinkedTitlesDTO = Mock(MovieLinkedTitlesDTO)
		Set<List<String>> performers = Mock(Set)
		Set<List<String>> stuntPerformers = Mock(Set)
		Set<List<String>> standInPerformers = Mock(Set)
		Movie movie = Mock(Movie)

		when:
		moviePerformersLinkingWorkerComposite.link(movieLinkedTitlesDTO, movie)

		then: 'linking workers are used to process particulars sets of link lists'
		1 * movieLinkedTitlesDTO.getPerformers() >> performers
		1 * moviePerformersCharacterLinkingWorkerMock.link(performers, movie)
		1 * movieLinkedTitlesDTO.getStuntPerformers() >> stuntPerformers
		1 * movieStuntPerformersLinkingWorkerMock.link(stuntPerformers, movie)
		1 * movieLinkedTitlesDTO.getStandInPerformers() >> standInPerformers
		1 * movieStandInPerformersLinkingWorkerMock.link(standInPerformers, movie)
		0 * _
	}

}
