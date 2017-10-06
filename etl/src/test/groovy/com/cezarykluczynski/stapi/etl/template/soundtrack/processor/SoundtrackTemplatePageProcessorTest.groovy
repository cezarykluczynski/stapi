package com.cezarykluczynski.stapi.etl.template.soundtrack.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.template.soundtrack.dto.SoundtrackTemplate
import com.cezarykluczynski.stapi.etl.soundtrack.creation.service.SoundtrackPageFilter
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import spock.lang.Specification

class SoundtrackTemplatePageProcessorTest extends Specification {

	private static final String TITLE_WITH_BRACKETS = 'TITLE (soundtrack)'
	private static final String TITLE = 'TITLE'

	private SoundtrackPageFilter soundtrackFilterMock

	private TemplateFinder templateFinderMock

	private PageBindingService pageBindingServiceMock

	private SoundtrackTemplateCompositeEnrichingProcessor soundtrackTemplateCompositeEnrichingProcessorMock

	private SoundtrackTemplatePageProcessor soundtrackTemplatePageProcessor

	void setup() {
		soundtrackFilterMock = Mock()
		templateFinderMock = Mock()
		pageBindingServiceMock = Mock()
		soundtrackTemplateCompositeEnrichingProcessorMock = Mock()
		soundtrackTemplatePageProcessor = new SoundtrackTemplatePageProcessor(soundtrackFilterMock, templateFinderMock, pageBindingServiceMock,
				soundtrackTemplateCompositeEnrichingProcessorMock)
	}

	void "when SoundtrackFilter returns true, null is returned"() {
		given:
		Page page = Mock()

		when:
		SoundtrackTemplate soundtrackTemplate = soundtrackTemplatePageProcessor.process(page)

		then:
		1 * soundtrackFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		soundtrackTemplate == null
	}

	void "when sidebar soundtrack template is not found, basic SoundtrackTemplate is returned"() {
		given:
		Page page = new Page(title: TITLE_WITH_BRACKETS)
		ModelPage modelPage = Mock()

		when:
		SoundtrackTemplate soundtrackTemplate = soundtrackTemplatePageProcessor.process(page)

		then:
		1 * soundtrackFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_SOUNDTRACK) >> Optional.empty()
		0 * _
		soundtrackTemplate.title == TITLE
		soundtrackTemplate.page == modelPage
	}

	void "when sidebar soundtrack template is found, it is passed along with SoundtrackTemplate to enriching processor"() {
		given:
		Page page = new Page(title: TITLE_WITH_BRACKETS)
		ModelPage modelPage = Mock()
		Template template = Mock()

		when:
		SoundtrackTemplate soundtrackTemplate = soundtrackTemplatePageProcessor.process(page)

		then:
		1 * soundtrackFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_SOUNDTRACK) >> Optional.of(template)
		1 * soundtrackTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Template, SoundtrackTemplate> enrichablePair ->
			assert enrichablePair.input == template
			assert enrichablePair.output != null
		}
		0 * _
		soundtrackTemplate.title == TITLE
		soundtrackTemplate.page == modelPage
	}

}
