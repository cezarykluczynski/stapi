package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.etl.movie.creation.dto.MovieLinkedTitlesDTO
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieClosingCreditsProcessor
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieLinkedTitlesProcessor
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class MovieRealPeopleLinkingWorkerCompositeTest extends Specification {

	private MovieClosingCreditsProcessor movieClosingCreditsProcessorMock

	private MovieLinkedTitlesProcessor movieLinkedTitlesProcessorMock

	private MovieWritersLinkingWorker movieWritersLinkingWorkerMock

	private MovieScreenplayAuthorsLinkingWorker movieScreenplayAuthorsLinkingWorkerMock

	private MovieStoryAuthorsLinkingWorker movieStoryAuthorsLinkingWorkerMock

	private MovieDirectorsLinkingWorker movieDirectorsLinkingWorkerMock

	private MovieProducersLinkingWorker movieProducersLinkingWorkerMock

	private MovieRealPeopleLinkingWorkerComposite movieRealPeopleLinkingWorkerComposite

	def setup() {
		movieClosingCreditsProcessorMock = Mock(MovieClosingCreditsProcessor)
		movieLinkedTitlesProcessorMock = Mock(MovieLinkedTitlesProcessor)
		movieWritersLinkingWorkerMock = Mock(MovieWritersLinkingWorker)
		movieScreenplayAuthorsLinkingWorkerMock = Mock(MovieScreenplayAuthorsLinkingWorker)
		movieStoryAuthorsLinkingWorkerMock = Mock(MovieStoryAuthorsLinkingWorker)
		movieDirectorsLinkingWorkerMock = Mock(MovieDirectorsLinkingWorker)
		movieProducersLinkingWorkerMock = Mock(MovieProducersLinkingWorker)
		movieRealPeopleLinkingWorkerComposite = new MovieRealPeopleLinkingWorkerComposite(
				movieClosingCreditsProcessorMock, movieLinkedTitlesProcessorMock, movieWritersLinkingWorkerMock,
				movieScreenplayAuthorsLinkingWorkerMock, movieStoryAuthorsLinkingWorkerMock,
				movieDirectorsLinkingWorkerMock, movieProducersLinkingWorkerMock)
	}

	def "gets closing credits, then gets titles in sections, then passes results to movie linkers"() {
		given:
		MovieLinkedTitlesDTO movieLinkedTitlesDTO = Mock(MovieLinkedTitlesDTO)
		List<PageSection> pageSectionList = Lists.newArrayList()
		Set<List<String>> writers = Mock(Set)
		Set<List<String>> screenplayAuthors = Mock(Set)
		Set<List<String>> storyAuthors = Mock(Set)
		Set<List<String>> directors = Mock(Set)
		Set<List<String>> producers = Mock(Set)
		Page page = Mock(Page)
		Movie movie = Mock(Movie)

		when:
		movieRealPeopleLinkingWorkerComposite.link(page, movie)

		then:
		1 * movieClosingCreditsProcessorMock.process(page) >> pageSectionList
		1 * movieLinkedTitlesProcessorMock.process(pageSectionList) >> movieLinkedTitlesDTO

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
		0 * _
	}

}
