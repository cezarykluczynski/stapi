package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.etl.movie.creation.dto.MovieLinkedTitlesDTO
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieClosingCreditsProcessor
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieLinkedTitlesProcessor
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification

class MovieRealPeopleLinkingWorkerCompositeTest extends Specification {

	private MovieClosingCreditsProcessor movieClosingCreditsProcessorMock

	private MovieLinkedTitlesProcessor movieLinkedTitlesProcessorMock

	private MovieStaffLinkingWorkerComposite movieStaffLinkingWorkerCompositeMock

	private MoviePerformersLinkingWorkerComposite moviePerformersLinkingWorkerCompositeMock

	private MovieRealPeopleLinkingWorkerComposite movieRealPeopleLinkingWorkerComposite

	void setup() {
		movieClosingCreditsProcessorMock = Mock()
		movieLinkedTitlesProcessorMock = Mock()
		movieStaffLinkingWorkerCompositeMock = Mock()
		moviePerformersLinkingWorkerCompositeMock = Mock()
		movieRealPeopleLinkingWorkerComposite = new MovieRealPeopleLinkingWorkerComposite(movieClosingCreditsProcessorMock,
				movieLinkedTitlesProcessorMock, movieStaffLinkingWorkerCompositeMock, moviePerformersLinkingWorkerCompositeMock)
	}

	void "gets closing credits, then gets titles in sections, then passes results to movie linkers"() {
		given:
		MovieLinkedTitlesDTO movieLinkedTitlesDTO = Mock()
		Page page = Mock()
		Movie movie = Mock()
		Pair<List<PageSection>, Page> pair = Mock()

		when:
		movieRealPeopleLinkingWorkerComposite.link(page, movie)

		then:
		1 * movieClosingCreditsProcessorMock.process(page) >> pair
		1 * movieLinkedTitlesProcessorMock.process(pair) >> movieLinkedTitlesDTO
		1 * movieStaffLinkingWorkerCompositeMock.link(movieLinkedTitlesDTO, movie)
		1 * moviePerformersLinkingWorkerCompositeMock.link(movieLinkedTitlesDTO, movie)
		0 * _
	}

}
