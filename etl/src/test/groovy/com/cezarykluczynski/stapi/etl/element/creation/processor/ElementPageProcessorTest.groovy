package com.cezarykluczynski.stapi.etl.element.creation.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.common.processor.TemplateTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.element.creation.service.ElementPageFilter
import com.cezarykluczynski.stapi.etl.template.element.processor.ElementTemplateEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.element.entity.Element
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page as SourcesPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class ElementPageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_WITH_BRACKETS = 'TITLE (with brackets)'
	private static final String UID = 'UID'
	private static final String TEMPLATE_TITLE = 'TEMPLATE_TITLE'
	private static final String SYMBOL = 'SYMBOL'

	private ElementPageFilter elementPageFilterMock

	private PageBindingService pageBindingServiceMock

	private UidGenerator uidGeneratorMock

	private TemplateFinder templateFinderMock

	private ElementTemplateEnrichingProcessor elementTemplateEnrichingProcessorMock

	private TemplateTitlesExtractingProcessor templateTitlesExtractingProcessorMock

	private ElementTemplateTitlesEnrichingProcessor elementTemplateTitlesEnrichingProcessorMock

	private ElementTransuraniumProcessor elementTransuraniumProcessorMock

	private ElementSymbolFixedValueProvider elementSymbolFixedValueProviderMock

	private ElementPageProcessor elementPageProcessor

	void setup() {
		elementPageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		uidGeneratorMock = Mock()
		templateFinderMock = Mock()
		elementTemplateEnrichingProcessorMock = Mock()
		templateTitlesExtractingProcessorMock = Mock()
		elementTemplateTitlesEnrichingProcessorMock = Mock()
		elementTransuraniumProcessorMock = Mock()
		elementSymbolFixedValueProviderMock = Mock()
		elementPageProcessor = new ElementPageProcessor(elementPageFilterMock, pageBindingServiceMock, uidGeneratorMock, templateFinderMock,
				elementTemplateEnrichingProcessorMock, templateTitlesExtractingProcessorMock, elementTemplateTitlesEnrichingProcessorMock,
				elementTransuraniumProcessorMock, elementSymbolFixedValueProviderMock)
	}

	void "should return null when page should be filtered out"() {
		given:
		SourcesPage page = Mock()

		when:
		Element element = elementPageProcessor.process(page)

		then:
		1 * elementPageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		element == null
	}

	void "parses page with title in brackets, and without sidebar element template nor symbol fixed value holder"() {
		given:
		Page modelPage = Mock()
		Template template = Mock()
		SourcesPage page = new SourcesPage(
				title: TITLE_WITH_BRACKETS,
				templates: Lists.newArrayList(template))

		when:
		Element element = elementPageProcessor.process(page)

		then:
		1 * elementPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, Element) >> UID
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_ELEMENT) >> Optional.empty()
		1 * templateTitlesExtractingProcessorMock.process(Lists.newArrayList(template)) >> Lists.newArrayList(TEMPLATE_TITLE)
		1 * elementTemplateTitlesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair<List<String>, Element> enrichablePair ->
			assert enrichablePair.input[0] == TEMPLATE_TITLE
			assert enrichablePair.output != null
		}
		1 * elementTransuraniumProcessorMock.isTransuranium(TITLE_WITH_BRACKETS) >> true
		1 * elementSymbolFixedValueProviderMock.getSearchedValue(TITLE_WITH_BRACKETS) >> FixedValueHolder.empty()
		0 * _
		element.name == TITLE
		element.uid == UID
		element.symbol == null
		element.transuranium
	}

	void "parses page without title in brackets, and with sidebar element template, and with symbol fixed value holder"() {
		given:
		Page modelPage = Mock()
		Template.Part templatePart = Mock()
		Template template = Mock()
		Template sidebarElementTemplate = new Template(parts: Lists.newArrayList(templatePart))
		SourcesPage page = new SourcesPage(
				title: TITLE,
				templates: Lists.newArrayList(template))

		when:
		Element element = elementPageProcessor.process(page)

		then:
		1 * elementPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * uidGeneratorMock.generateFromPage(modelPage, Element) >> UID
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_ELEMENT) >> Optional.of(sidebarElementTemplate)
		1 * elementTemplateEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair<List<Template.Part>, Element> enrichablePair ->
			assert enrichablePair.input == Lists.newArrayList(templatePart)
			assert enrichablePair.output != null
		}
		1 * templateTitlesExtractingProcessorMock.process(Lists.newArrayList(template)) >> Lists.newArrayList(TEMPLATE_TITLE)
		1 * elementTemplateTitlesEnrichingProcessorMock.enrich(_ as EnrichablePair)
		1 * elementTransuraniumProcessorMock.isTransuranium(TITLE) >> false
		1 * elementSymbolFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.found(SYMBOL)
		0 * _
		element.name == TITLE
		element.uid == UID
		element.symbol == SYMBOL
		!element.transuranium
	}

}
