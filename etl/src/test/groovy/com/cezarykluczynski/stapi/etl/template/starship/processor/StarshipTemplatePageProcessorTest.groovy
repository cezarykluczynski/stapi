package com.cezarykluczynski.stapi.etl.template.starship.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate
import com.cezarykluczynski.stapi.etl.template.starship.service.StarshipFilter
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import spock.lang.Specification

class StarshipTemplatePageProcessorTest extends Specification {

	private static final String TITLE_WITH_BRACKETS = 'TITLE (something in brackets)'
	private static final String TITLE = 'TITLE'

	private StarshipFilter starshipFilterMock

	private TemplateFinder templateFinderMock

	private PageBindingService pageBindingServiceMock

	private StarshipTemplateCompositeEnrichingProcessor starshipTemplateCompositeEnrichingProcessorMock

	private StarshipTemplatePageProcessor starshipTemplatePageProcessor

	void setup() {
		starshipFilterMock = Mock()
		templateFinderMock = Mock()
		pageBindingServiceMock = Mock()
		starshipTemplateCompositeEnrichingProcessorMock = Mock()
		starshipTemplatePageProcessor = new StarshipTemplatePageProcessor(starshipFilterMock, templateFinderMock, pageBindingServiceMock,
				starshipTemplateCompositeEnrichingProcessorMock)
	}

	void "when StarshipFilter returns true, null is returned"() {
		given:
		Page page = Mock()

		when:
		StarshipTemplate starshipTemplate = starshipTemplatePageProcessor.process(page)

		then:
		1 * starshipFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		starshipTemplate == null
	}

	void "when sidebar starship template is not found, basic StarshipTemplate is returned"() {
		given:
		List<CategoryHeader> categoryHeaderList = Mock()
		Page page = new Page(
				title: TITLE_WITH_BRACKETS,
				categories: categoryHeaderList)
		ModelPage modelPage = Mock()

		when:
		StarshipTemplate starshipTemplate = starshipTemplatePageProcessor.process(page)

		then:
		1 * starshipFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_STARSHIP) >> Optional.empty()
		0 * _
		starshipTemplate.name == TITLE
		starshipTemplate.page == modelPage
	}

	void "when sidebar starship template is found, it is passed along with StarshipTemplate to enriching processor"() {
		given:
		List<CategoryHeader> categoryHeaderList = Mock()
		Page page = new Page(
				title: TITLE_WITH_BRACKETS,
				categories: categoryHeaderList)
		ModelPage modelPage = Mock()
		Template template = Mock()

		when:
		StarshipTemplate starshipTemplate = starshipTemplatePageProcessor.process(page)

		then:
		1 * starshipFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_STARSHIP) >> Optional.of(template)
		1 * starshipTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Template, StarshipTemplate> enrichablePair ->
			assert enrichablePair.input == template
			assert enrichablePair.output != null
		}
		0 * _
		starshipTemplate.name == TITLE
		starshipTemplate.page == modelPage
	}

}
