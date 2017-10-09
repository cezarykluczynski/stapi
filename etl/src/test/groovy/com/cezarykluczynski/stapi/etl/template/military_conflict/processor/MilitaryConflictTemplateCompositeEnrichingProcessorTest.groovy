package com.cezarykluczynski.stapi.etl.template.military_conflict.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class MilitaryConflictTemplateCompositeEnrichingProcessorTest extends Specification {

	private static final CATEGORY_TITLE_1 = 'CATEGORY_TITLE_1'
	private static final TEMPLATE_TITLE_1 = 'TEMPLATE_TITLE_1'
	private static final TEMPLATE_TITLE_2 = 'TEMPLATE_TITLE_2'

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private MilitaryConflictTemplateCategoriesEnrichingProcessor militaryConflictTemplateCategoriesEnrichingProcessorMock

	private MilitaryConflictTemplateTemplatesEnrichingProcessor militaryConflictTemplateTemplatesEnrichingProcessorMock

	private MilitaryConflictTemplateCompositeEnrichingProcessor militaryConflictTemplateCompositeEnrichingProcessor

	void setup() {
		categoryTitlesExtractingProcessorMock = Mock()
		militaryConflictTemplateCategoriesEnrichingProcessorMock = Mock()
		militaryConflictTemplateTemplatesEnrichingProcessorMock = Mock()
		militaryConflictTemplateCompositeEnrichingProcessor = new MilitaryConflictTemplateCompositeEnrichingProcessor(
				categoryTitlesExtractingProcessorMock, militaryConflictTemplateCategoriesEnrichingProcessorMock,
				militaryConflictTemplateTemplatesEnrichingProcessorMock)
	}

	void "passes parts of a given page to dependencies"() {
		given:
		CategoryHeader categoryHeader = Mock()
		Template template1 = new Template(title: TEMPLATE_TITLE_1)
		Template template2 = new Template(title: TEMPLATE_TITLE_2)
		Page page = new Page(
				categories: Lists.newArrayList(categoryHeader),
				templates: Lists.newArrayList(template1, template2)
		)
		MilitaryConflictTemplate militaryConflictTemplate = new MilitaryConflictTemplate()

		when:
		militaryConflictTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(page, militaryConflictTemplate))

		then:
		1 * categoryTitlesExtractingProcessorMock.process(Lists.newArrayList(categoryHeader)) >> Lists.newArrayList(CATEGORY_TITLE_1)
		1 * militaryConflictTemplateCategoriesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<List<String>, MilitaryConflictTemplate> enrichablePair ->
			enrichablePair.input == Lists.newArrayList(CATEGORY_TITLE_1)
			enrichablePair.output == militaryConflictTemplate
		}
		1 * militaryConflictTemplateTemplatesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<List<String>, MilitaryConflictTemplate> enrichablePair ->
			enrichablePair.input == Lists.newArrayList(TEMPLATE_TITLE_1, TEMPLATE_TITLE_2)
			enrichablePair.output == militaryConflictTemplate
		}
		0 * _
	}

}
