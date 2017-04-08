package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.etl.movie.creation.dto.MovieLinkedTitlesDTO
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import spock.lang.Specification

class MoviePerformersLinkingWorkerCompositeTest extends Specification {

	private MoviePerformersCharactersLinkingWorker moviePerformersCharacterLinkingWorkerMock

	private MovieStuntPerformersLinkingWorker movieStuntPerformersLinkingWorkerMock

	private MovieStandInPerformersLinkingWorker movieStandInPerformersLinkingWorkerMock

	private MoviePerformersLinkingWorkerComposite moviePerformersLinkingWorkerComposite

	void setup() {
		moviePerformersCharacterLinkingWorkerMock = Mock()
		movieStuntPerformersLinkingWorkerMock = Mock()
		movieStandInPerformersLinkingWorkerMock = Mock()
		moviePerformersLinkingWorkerComposite = new MoviePerformersLinkingWorkerComposite(moviePerformersCharacterLinkingWorkerMock,
				movieStuntPerformersLinkingWorkerMock, movieStandInPerformersLinkingWorkerMock)
	}

	void "passes the right sets of performers to particular workers"() {
		given:
		MovieLinkedTitlesDTO movieLinkedTitlesDTO = Mock()
		Set<List<String>> performers = Mock()
		Set<List<String>> stuntPerformers = Mock()
		Set<List<String>> standInPerformers = Mock()
		Movie movie = Mock()

		when:
		moviePerformersLinkingWorkerComposite.link(movieLinkedTitlesDTO, movie)

		then: 'linking workers are used to process particulars sets of link lists'
		1 * movieLinkedTitlesDTO.performers >> performers
		1 * moviePerformersCharacterLinkingWorkerMock.link(performers, movie)
		1 * movieLinkedTitlesDTO.stuntPerformers >> stuntPerformers
		1 * movieStuntPerformersLinkingWorkerMock.link(stuntPerformers, movie)
		1 * movieLinkedTitlesDTO.standInPerformers >> standInPerformers
		1 * movieStandInPerformersLinkingWorkerMock.link(standInPerformers, movie)
		0 * _
	}

}
