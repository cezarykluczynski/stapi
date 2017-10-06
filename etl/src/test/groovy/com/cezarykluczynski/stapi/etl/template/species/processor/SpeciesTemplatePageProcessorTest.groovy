package com.cezarykluczynski.stapi.etl.template.species.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.etl.template.species.dto.SpeciesTemplate
import com.cezarykluczynski.stapi.etl.species.creation.service.SpeciesPageFilter
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page as SourcesPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class SpeciesTemplatePageProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_WITH_BRACKETS = 'TITLE (species)'

	private SpeciesPageFilter speciesTemplateFilterMock

	private PageBindingService pageBindingServiceMock

	private TemplateFinder templateFinderMock

	private SpeciesTemplatePartsEnrichingProcessor speciesTemplatePartsEnrichingProcessorMock

	private SpeciesTemplateTypeEnrichingProcessor speciesTemplateTypeEnrichingProcessorMock

	private SpeciesTemplatePageProcessor speciesTemplatePageProcessor

	void setup() {
		speciesTemplateFilterMock = Mock()
		pageBindingServiceMock = Mock()
		templateFinderMock = Mock()
		speciesTemplatePartsEnrichingProcessorMock = Mock()
		speciesTemplateTypeEnrichingProcessorMock = Mock()
		speciesTemplatePageProcessor = new SpeciesTemplatePageProcessor(speciesTemplateFilterMock, pageBindingServiceMock, templateFinderMock,
				speciesTemplatePartsEnrichingProcessorMock, speciesTemplateTypeEnrichingProcessorMock)
	}

	void "returns nul when SpeciesTemplateFilter returns true"() {
		given:
		SourcesPage page = new SourcesPage()

		when:
		SpeciesTemplate speciesTemplate = speciesTemplatePageProcessor.process(page)

		then:
		1 * speciesTemplateFilterMock.shouldBeFilteredOut(page) >> true
		0 * _
		speciesTemplate == null
	}

	void "cleans title"() {
		given:
		SourcesPage page = new SourcesPage(title: TITLE_WITH_BRACKETS)
		ModelPage modelPage = new ModelPage()

		when:
		SpeciesTemplate speciesTemplate = speciesTemplatePageProcessor.process(page)

		then:
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_SPECIES) >> Optional.empty()
		speciesTemplate.name == TITLE
		speciesTemplate.page == modelPage
	}

	void "returns SpeciesTemplate when it is valid"() {
		given:
		SourcesPage page = new SourcesPage(title: TITLE)
		ModelPage modelPage = new ModelPage()

		when:
		SpeciesTemplate speciesTemplate = speciesTemplatePageProcessor.process(page)

		then:
		1 * speciesTemplateFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * speciesTemplateTypeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> { EnrichablePair<SourcesPage, SpeciesTemplate> enrichablePair ->
			assert enrichablePair.input == page
			assert enrichablePair.output != null
		}
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_SPECIES) >> Optional.empty()
		0 * _
		speciesTemplate.name == TITLE
		speciesTemplate.page == modelPage
	}

	void "passes SpeciesTemplate to SpeciesTemplatePartsEnrichingProcessor when 'sidebar species' template was found on page"() {
		given:
		SourcesPage page = new SourcesPage(title: TITLE)
		ModelPage modelPage = new ModelPage()
		Template.Part templatePart = Mock()
		Template template = new Template(parts: Lists.newArrayList(templatePart))

		when:
		SpeciesTemplate speciesTemplate = speciesTemplatePageProcessor.process(page)

		then:
		1 * speciesTemplateFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * speciesTemplateTypeEnrichingProcessorMock.enrich(_ as EnrichablePair)
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_SPECIES) >> Optional.of(template)
		1 * speciesTemplatePartsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<List<Template.Part>, SpeciesTemplate> enrichablePair ->
			assert enrichablePair.input.contains(templatePart)
			assert enrichablePair.output != null
		}
		0 * _
		speciesTemplate != null
	}

}
