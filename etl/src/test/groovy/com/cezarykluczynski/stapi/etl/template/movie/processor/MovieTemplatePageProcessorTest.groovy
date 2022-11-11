package com.cezarykluczynski.stapi.etl.template.movie.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.episode.creation.dto.ModuleEpisodeData
import com.cezarykluczynski.stapi.etl.episode.creation.service.ModuleEpisodeDataProvider
import com.cezarykluczynski.stapi.etl.movie.creation.service.MoviePageFilter
import com.cezarykluczynski.stapi.etl.template.movie.linker.MovieRealPeopleLinkingWorkerComposite
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.page.entity.Page as PageEntity
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class MovieTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final Template SIDEBAR_FILM_TEMPLATE = new Template(title: TemplateTitle.SIDEBAR_FILM)

	private MoviePageFilter moviePageFilterMock

	private MovieTemplateProcessor movieTemplateProcessorMock

	private TemplateFinder templateFinderMock

	private PageBindingService pageBindingServiceMock

	private MovieTemplateTitleLanguagesEnrichingProcessor movieTemplateTitleLanguagesEnrichingProcessorMock

	private MovieRealPeopleLinkingWorkerComposite moviePerformancesLinkingWorkerMock

	private ModuleEpisodeDataProvider moduleEpisodeDataProviderMock

	private ModuleMovieDataEnrichingProcessor moduleMovieDataEnrichingProcessorMock

	private MovieTemplatePageProcessor movieTemplatePageProcessor

	void setup() {
		moviePageFilterMock = Mock()
		movieTemplateProcessorMock = Mock()
		templateFinderMock = Mock()
		pageBindingServiceMock = Mock()
		movieTemplateTitleLanguagesEnrichingProcessorMock = Mock()
		moviePerformancesLinkingWorkerMock = Mock()
		moduleEpisodeDataProviderMock = Mock()
		moduleMovieDataEnrichingProcessorMock = Mock()
		movieTemplatePageProcessor = new MovieTemplatePageProcessor(moviePageFilterMock, movieTemplateProcessorMock, templateFinderMock,
				pageBindingServiceMock, movieTemplateTitleLanguagesEnrichingProcessorMock, moviePerformancesLinkingWorkerMock,
				moduleEpisodeDataProviderMock, moduleMovieDataEnrichingProcessorMock)
	}

	void "does not interact with dependencies the MoviePageFilter returns true"() {
		given:
		Page page = new Page()

		when:
		movieTemplatePageProcessor.process(page)

		then:
		1 * moviePageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
	}

	void "does not interact dependencies further when template was not found"() {
		given:
		Page page = new Page()

		when:
		movieTemplatePageProcessor.process(page)

		then:
		1 * moviePageFilterMock.shouldBeFilteredOut(page) >> false
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_FILM) >> Optional.empty()
		0 * _
	}

	void "page is processed using dependencies"() {
		given:
		Page page = new Page(
				title: TITLE,
				templates: Lists.newArrayList(SIDEBAR_FILM_TEMPLATE)
		)
		Movie movieStub = new Movie()
		MovieTemplate movieTemplate = new MovieTemplate(movieStub: movieStub)
		PageEntity pageEntity = new PageEntity()
		ModuleEpisodeData moduleEpisodeData = Mock()

		when:
		MovieTemplate movieTemplateOutput = movieTemplatePageProcessor.process(page)

		then:
		1 * moviePageFilterMock.shouldBeFilteredOut(page) >> false
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_FILM) >> Optional.of(SIDEBAR_FILM_TEMPLATE)
		1 * movieTemplateProcessorMock.process(SIDEBAR_FILM_TEMPLATE) >> movieTemplate
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> pageEntity
		1 * moduleEpisodeDataProviderMock.provideDataFor(TITLE) >> moduleEpisodeData
		1 * moduleMovieDataEnrichingProcessorMock.enrich(_) >> { EnrichablePair<ModuleEpisodeData, MovieTemplate> enrichablePair ->
			assert enrichablePair.input == moduleEpisodeData
			assert enrichablePair.output == movieTemplate
		}
		1 * movieTemplateTitleLanguagesEnrichingProcessorMock.enrich(_) >> { EnrichablePair<Page, MovieTemplate> enrichablePair ->
			assert enrichablePair.input == page
			assert enrichablePair.output == movieTemplate
		}
		1 * moviePerformancesLinkingWorkerMock.link(page, movieStub)
		movieTemplateOutput == movieTemplate
		movieTemplateOutput.page == pageEntity

		then: 'no other interactions are expected'
		0 * _
	}
}
