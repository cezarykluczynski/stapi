package com.cezarykluczynski.stapi.etl.template.movie.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.common.linker.MovieRealPeopleLinkingWorker
import com.cezarykluczynski.stapi.etl.template.movie.dto.MovieTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.page.entity.Page as PageEntity
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.PageName
import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.google.common.collect.Lists
import spock.lang.Specification

class ToMovieTemplateProcessorTest extends Specification {

	private final Template SIDEBAR_FILM_TEMPLATE = new Template(
			title: TemplateName.SIDEBAR_FILM
	)

	private MovieTemplateProcessor movieTemplateProcessorMock

	private TemplateFinder templateFinderMock

	private PageBindingService pageBindingServiceMock

	private MovieTemplateTitleLanguagesEnrichingProcessor movieTemplateTitleLanguagesEnrichingProcessorMock

	private MovieRealPeopleLinkingWorker moviePerformancesLinkingWorkerMock

	private ToMovieTemplateProcessor toMovieTemplateProcessor

	def setup() {
		movieTemplateProcessorMock = Mock(MovieTemplateProcessor)
		templateFinderMock = Mock(TemplateFinder)
		pageBindingServiceMock = Mock(PageBindingService)
		movieTemplateTitleLanguagesEnrichingProcessorMock = Mock(MovieTemplateTitleLanguagesEnrichingProcessor)
		moviePerformancesLinkingWorkerMock = Mock(MovieRealPeopleLinkingWorker)
		toMovieTemplateProcessor = new ToMovieTemplateProcessor(movieTemplateProcessorMock, templateFinderMock,
				pageBindingServiceMock, movieTemplateTitleLanguagesEnrichingProcessorMock,
				moviePerformancesLinkingWorkerMock)
	}

	def "does not interact with dependencies other than TemplateFinder when template was not found"() {
		given:
		Page page = new Page()

		when:
		toMovieTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_FILM) >> Optional.empty()
		0 * _
	}

	def "returns null when page is Star Trek films"() {
		given:
		Page page = new Page(title: PageName.STAR_TREK_FILMS)

		when:
		MovieTemplate movieTemplate = toMovieTemplateProcessor.process(page)

		then:
		movieTemplate == null
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_FILM) >> Optional.of(SIDEBAR_FILM_TEMPLATE)
		0 * _
	}

	def "returns null when page is Star Trek XIV"() {
		given:
		Page page = new Page(title: PageName.STAR_TREK_XIV)

		when:
		MovieTemplate movieTemplate = toMovieTemplateProcessor.process(page)

		then:
		movieTemplate == null
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_FILM) >> Optional.of(SIDEBAR_FILM_TEMPLATE)
		0 * _
	}

	def "page is processed using dependencies"() {
		given:
		Page page = new Page(
				templates: Lists.newArrayList(SIDEBAR_FILM_TEMPLATE)
		)
		Movie movieStub = new Movie()
		MovieTemplate movieTemplate = new MovieTemplate(movieStub: movieStub)
		PageEntity pageEntity = new PageEntity()

		when:
		MovieTemplate movieTemplateOutput = toMovieTemplateProcessor.process(page)

		then:
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_FILM) >> Optional.of(SIDEBAR_FILM_TEMPLATE)
		1 * movieTemplateProcessorMock.process(SIDEBAR_FILM_TEMPLATE) >> movieTemplate
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> pageEntity
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
