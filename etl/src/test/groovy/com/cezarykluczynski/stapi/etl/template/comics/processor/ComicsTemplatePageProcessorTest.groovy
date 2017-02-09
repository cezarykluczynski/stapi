package com.cezarykluczynski.stapi.etl.template.comics.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateName
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicsTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'

	private PageBindingService pageBindingServiceMock

	private TemplateFinder templateFinderMock

	private ComicsTemplateFixedValuesEnrichingProcessor comicsTemplateFixedValuesEnrichingProcessorMock

	private ComicsTemplateCompositeEnrichingProcessor comicsTemplateCompositeEnrichingProcessorMock

	private ComicsTemplatePageProcessor comicsTemplatePageProcessor

	void setup() {
		pageBindingServiceMock = Mock(PageBindingService)
		templateFinderMock = Mock(TemplateFinder)
		comicsTemplateFixedValuesEnrichingProcessorMock = Mock(ComicsTemplateFixedValuesEnrichingProcessor)
		comicsTemplateCompositeEnrichingProcessorMock = Mock(ComicsTemplateCompositeEnrichingProcessor)
		comicsTemplatePageProcessor = new ComicsTemplatePageProcessor(pageBindingServiceMock, templateFinderMock,
				comicsTemplateFixedValuesEnrichingProcessorMock, comicsTemplateCompositeEnrichingProcessorMock)
	}

	void "returns null when page title is among invalid page titles"() {
		given:
		Page page = new Page(title: ComicsTemplatePageProcessor.INVALID_TITLES[0])

		when:
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(page)

		then:
		comicsTemplate == null
	}

	void "parses page that does not have sidebar comics template"() {
		given:
		Page page = new Page(title: TITLE)
		ModelPage modelPage = new ModelPage()

		when:
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * comicsTemplateFixedValuesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair enrichablePair ->
			assert enrichablePair.input != null
			assert enrichablePair.output != null
		}
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_COMIC) >> Optional.empty()
		0 * _
		comicsTemplate.title == TITLE
		comicsTemplate.page == modelPage
		!comicsTemplate.productOfRedirect
	}

	void "parses page with sidebar comics template"() {
		given:
		Page page = new Page(
				title: TITLE,
				redirectPath: Lists.newArrayList(Mock(PageHeader)))
		ModelPage modelPage = new ModelPage()
		Template template = Mock(Template)

		when:
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * comicsTemplateFixedValuesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair enrichablePair ->
			assert enrichablePair.input != null
			assert enrichablePair.output != null
		}
		1 * templateFinderMock.findTemplate(page, TemplateName.SIDEBAR_COMIC) >> Optional.of(template)
		1 * comicsTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair<Template, ComicsTemplate> enrichablePair ->

		}
		0 * _
		comicsTemplate.title == TITLE
		comicsTemplate.page == modelPage
		comicsTemplate.productOfRedirect
	}

}
