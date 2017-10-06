package com.cezarykluczynski.stapi.etl.template.video_game.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplate
import com.cezarykluczynski.stapi.etl.video_game.creation.service.VideoGamePageFilter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import spock.lang.Specification
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage

class VideoGameTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_WITH_BRACKETS = 'TITLE (with brackets)'

	private VideoGamePageFilter videoGameFilterMock

	private TemplateFinder templateFinderMock

	private PageBindingService pageBindingServiceMock

	private VideoGameTemplateCompositeEnrichingProcessor videoGameCompositeEnrichingProcessorMock

	private VideoGameTemplatePageProcessor videoGameTemplatePageProcessor

	void setup() {
		videoGameFilterMock = Mock()
		templateFinderMock = Mock()
		pageBindingServiceMock = Mock()
		videoGameCompositeEnrichingProcessorMock = Mock()
		videoGameTemplatePageProcessor = new VideoGameTemplatePageProcessor(videoGameFilterMock, templateFinderMock, pageBindingServiceMock,
				videoGameCompositeEnrichingProcessorMock)
	}

	void "should return null when page should be filtered out"() {
		given:
		Page page = new Page()

		when:
		VideoGameTemplate videoGameTemplate = videoGameTemplatePageProcessor.process(page)

		then:
		1 * videoGameFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		videoGameTemplate == null
	}

	void "when template is not found on page, null is returned"() {
		given:
		Page page = new Page()

		when:
		VideoGameTemplate videoGameTemplate = videoGameTemplatePageProcessor.process(page)

		then:
		1 * videoGameFilterMock.shouldBeFilteredOut(page) >> false
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_VIDEOGAME) >> Optional.empty()
		0 * _
		videoGameTemplate == null
	}

	void "cleans title, sets page, then passed template to VideoGameCompositeEnrichingProcessor, then returns result"() {
		given:
		Page page = new Page(title: TITLE_WITH_BRACKETS)
		Template template = Mock()
		ModelPage modelPage = Mock()

		when:
		VideoGameTemplate videoGameTemplate = videoGameTemplatePageProcessor.process(page)

		then:
		1 * videoGameFilterMock.shouldBeFilteredOut(page) >> false
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_VIDEOGAME) >> Optional.of(template)
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * videoGameCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair<Template, VideoGameTemplate> enrichablePair ->
			assert enrichablePair.input == template
			assert enrichablePair.output != null
		}
		0 * _
		videoGameTemplate.title == TITLE
		videoGameTemplate.page == modelPage
	}

}
