package com.cezarykluczynski.stapi.etl.template.military_conflict.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService
import com.cezarykluczynski.stapi.etl.conflict.creation.service.ConflictPageFilter
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder
import com.cezarykluczynski.stapi.model.page.entity.Page as ModelPage
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.cezarykluczynski.stapi.util.constant.TemplateTitle
import com.google.common.collect.Lists
import spock.lang.Specification

class MilitaryConflictTemplatePageProcessorTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String TITLE_WITH_BRACKETS = 'NAME (with brackets)'

	private ConflictPageFilter conflictPageFilterMock

	private PageBindingService pageBindingServiceMock

	private TemplateFinder templateFinderMock

	private MilitaryConflictTemplatePartsEnrichingProcessor militaryConflictTemplatePartsEnrichingProcessorMock

	private MilitaryConflictTemplateCompositeEnrichingProcessor militaryConflictTemplateCompositeEnrichingProcessorMock

	private MilitaryConflictTemplatePageProcessor militaryConflictTemplatePageProcessor

	void setup() {
		conflictPageFilterMock = Mock()
		pageBindingServiceMock = Mock()
		templateFinderMock = Mock()
		militaryConflictTemplatePartsEnrichingProcessorMock = Mock()
		militaryConflictTemplateCompositeEnrichingProcessorMock = Mock()
		militaryConflictTemplatePageProcessor = new MilitaryConflictTemplatePageProcessor(conflictPageFilterMock, pageBindingServiceMock,
				templateFinderMock, militaryConflictTemplatePartsEnrichingProcessorMock, militaryConflictTemplateCompositeEnrichingProcessorMock)
	}

	void "returns null when ConflictPageFilter returns true"() {
		given:
		Page page = Mock()

		when:
		MilitaryConflictTemplate militaryConflictTemplate = militaryConflictTemplatePageProcessor.process(page)

		then:
		1 * conflictPageFilterMock.shouldBeFilteredOut(page) >> true
		militaryConflictTemplate == null
	}

	void "parses page without sidebar military conflict template"() {
		given:
		Page page = new Page(title: TITLE_WITH_BRACKETS)
		ModelPage modelPage = Mock()

		when:
		MilitaryConflictTemplate militaryConflictTemplate = militaryConflictTemplatePageProcessor.process(page)

		then:
		1 * conflictPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MILITARY_CONFLICT) >> Optional.empty()
		1 * militaryConflictTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Page, MilitaryConflictTemplate> enrichablePair ->
				enrichablePair.input == page
				enrichablePair.output != null
		}
		0 * _
		militaryConflictTemplate.name == NAME
	}

	void "parses page with sidebar military conflict template"() {
		given:
		Template.Part templatePart = Mock()
		Template sidebarMilitaryConflictTemplate = new Template(parts: Lists.newArrayList(templatePart))
		Page page = new Page(title: NAME)
		ModelPage modelPage = Mock()

		when:
		MilitaryConflictTemplate militaryConflictTemplate = militaryConflictTemplatePageProcessor.process(page)

		then:
		1 * conflictPageFilterMock.shouldBeFilteredOut(page) >> false
		1 * pageBindingServiceMock.fromPageToPageEntity(page) >> modelPage
		1 * templateFinderMock.findTemplate(page, TemplateTitle.SIDEBAR_MILITARY_CONFLICT) >> Optional.of(sidebarMilitaryConflictTemplate)
		1 * militaryConflictTemplatePartsEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<List<Template.Part>, MilitaryConflictTemplate> enrichablePair ->
				enrichablePair.input == Lists.newArrayList(templatePart)
				enrichablePair.output != null
		}
		1 * militaryConflictTemplateCompositeEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<Page, MilitaryConflictTemplate> enrichablePair ->
				enrichablePair.input == page
				enrichablePair.output != null
		}
		0 * _
		militaryConflictTemplate.name == NAME
		militaryConflictTemplate.page == modelPage
	}
}
