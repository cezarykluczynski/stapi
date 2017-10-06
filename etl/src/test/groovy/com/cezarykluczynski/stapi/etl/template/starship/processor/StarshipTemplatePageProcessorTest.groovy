package com.cezarykluczynski.stapi.etl.template.starship.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.spacecraft.creation.processor.SpacecraftCategoriesEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.template.spacecraft.processor.SpacecraftTemplateCompositeEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.starship.dto.StarshipTemplate
import com.cezarykluczynski.stapi.etl.spacecraft.creation.service.SpacecraftPageFilter
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import spock.lang.Specification

class StarshipTemplatePageProcessorTest extends Specification {

	private static final String TITLE_WITH_BRACKETS = 'TITLE (something in brackets)'
	private static final String TITLE = 'TITLE'

	private SpacecraftPageFilter starshipPageFilterMock

	private TemplateFinder templateFinderMock

	private PageBindingService pageBindingServiceMock

	private SpacecraftTemplateCompositeEnrichingProcessor spacecraftTemplateCompositeEnrichingProcessorMock

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private SpacecraftCategoriesEnrichingProcessor spacecraftCategoriesEnrichingProcessorMock

	private StarshipTemplatePageProcessor starshipTemplatePageProcessor

	void setup() {
		starshipPageFilterMock = Mock()
		templateFinderMock = Mock()
		pageBindingServiceMock = Mock()
		spacecraftTemplateCompositeEnrichingProcessorMock = Mock()
		categoryTitlesExtractingProcessorMock = Mock()
		spacecraftCategoriesEnrichingProcessorMock = Mock()
		starshipTemplatePageProcessor = new StarshipTemplatePageProcessor(starshipPageFilterMock, templateFinderMock, pageBindingServiceMock,
				spacecraftTemplateCompositeEnrichingProcessorMock, categoryTitlesExtractingProcessorMock, spacecraftCategoriesEnrichingProcessorMock)
	}

	void "when StarshipFilter returns true, null is returned"() {
		given:
		Page page = Mock()

		when:
		StarshipTemplate starshipTemplate = starshipTemplatePageProcessor.process(page)

		then:
		1 * starshipPageFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		starshipTemplate == null
	}

	void "when not templates are not found, basic StarshipTemplate is returned"() {
		given:
		Page page = new Page(title: TITLE_WITH_BRACKETS)
		ModelPage modelPage = Mock()

		when:
		StarshipTemplate starshipTemplate = starshipTemplatePageProcessor.process(page)

		then:
		1 * starshipPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_STARSHIP) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_STATION) >> Optional.empty()
		1 * categoryTitlesExtractingProcessorMock.process(_)
		1 * spacecraftCategoriesEnrichingProcessorMock.enrich(_)
		0 * _
		starshipTemplate.name == TITLE
		starshipTemplate.page == modelPage
	}

	void "when sidebar starship template is found, it is passed along with StarshipTemplate to enriching processor"() {
		given:
		Page page = new Page(title: TITLE_WITH_BRACKETS)
		ModelPage modelPage = Mock()
		Template template = Mock()

		when:
		StarshipTemplate starshipTemplate = starshipTemplatePageProcessor.process(page)

		then:
		1 * starshipPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_STARSHIP) >> Optional.of(template)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_STATION) >> Optional.empty()
		1 * spacecraftTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<Template, StarshipTemplate> enrichablePair ->
			assert enrichablePair.input == template
			assert enrichablePair.output != null
		}
		1 * categoryTitlesExtractingProcessorMock.process(_)
		1 * spacecraftCategoriesEnrichingProcessorMock.enrich(_)
		0 * _
		starshipTemplate.name == TITLE
		starshipTemplate.page == modelPage
	}

	void "when sidebar station template is found, it is passed along with StarshipTemplate to enriching processor"() {
		given:
		Page page = new Page(title: TITLE_WITH_BRACKETS)
		ModelPage modelPage = Mock()
		Template template = Mock()

		when:
		StarshipTemplate starshipTemplate = starshipTemplatePageProcessor.process(page)

		then:
		1 * starshipPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_STARSHIP) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_STATION) >> Optional.of(template)
		1 * spacecraftTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template, StarshipTemplate> enrichablePair ->
				assert enrichablePair.input == template
				assert enrichablePair.output != null
		}
		1 * categoryTitlesExtractingProcessorMock.process(_)
		1 * spacecraftCategoriesEnrichingProcessorMock.enrich(_)
		0 * _
		starshipTemplate.name == TITLE
		starshipTemplate.page == modelPage
	}

	void "categories are passed to SpacecraftCategoriesEnrichingProcessor"() {
		given:
		List<CategoryHeader> categoryHeaderList = Mock()
		List<String> categoryTitleList = Mock()
		Page page = new Page(
				title: TITLE_WITH_BRACKETS,
				categories: categoryHeaderList)
		ModelPage modelPage = Mock()
		Template template = Mock()

		when:
		StarshipTemplate starshipTemplate = starshipTemplatePageProcessor.process(page)

		then:
		1 * starshipPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_STARSHIP) >> Optional.empty()
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_STATION) >> Optional.of(template)
		1 * spacecraftTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Template, StarshipTemplate> enrichablePair ->
				assert enrichablePair.input == template
				assert enrichablePair.output != null
		}
		1 * categoryTitlesExtractingProcessorMock.process(categoryHeaderList) >> categoryTitleList
		1 * spacecraftCategoriesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<List<String>, StarshipTemplate> enrichablePair ->
			enrichablePair.input == categoryTitleList
			enrichablePair.output != null
		}
		0 * _
		starshipTemplate != null
	}

}
