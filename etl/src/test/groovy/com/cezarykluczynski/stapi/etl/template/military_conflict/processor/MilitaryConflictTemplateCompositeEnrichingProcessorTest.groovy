package com.cezarykluczynski.stapi.etl.template.military_conflict.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.common.processor.TemplateTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.template.military_conflict.dto.MilitaryConflictTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class MilitaryConflictTemplateCompositeEnrichingProcessorTest extends Specification {

	private static final CATEGORY_TITLE_1 = 'CATEGORY_TITLE_1'
	private static final TEMPLATE_TITLE_1 = 'TEMPLATE_TITLE_1'

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private TemplateTitlesExtractingProcessor templateTitlesExtractingProcessorMock

	private MilitaryConflictTemplateCategoriesEnrichingProcessor militaryConflictTemplateCategoriesEnrichingProcessorMock

	private MilitaryConflictTemplateTemplatesEnrichingProcessor militaryConflictTemplateTemplatesEnrichingProcessorMock

	private MilitaryConflictTemplateCompositeEnrichingProcessor militaryConflictTemplateCompositeEnrichingProcessor

	void setup() {
		categoryTitlesExtractingProcessorMock = Mock()
		templateTitlesExtractingProcessorMock = Mock()
		militaryConflictTemplateCategoriesEnrichingProcessorMock = Mock()
		militaryConflictTemplateTemplatesEnrichingProcessorMock = Mock()
		militaryConflictTemplateCompositeEnrichingProcessor = new MilitaryConflictTemplateCompositeEnrichingProcessor(
				categoryTitlesExtractingProcessorMock, templateTitlesExtractingProcessorMock,
				militaryConflictTemplateCategoriesEnrichingProcessorMock, militaryConflictTemplateTemplatesEnrichingProcessorMock)
	}

	void "passes parts of a given page to dependencies"() {
		given:
		CategoryHeader categoryHeader = Mock()
		Template template = Mock()
		Page page = new Page(
				categories: Lists.newArrayList(categoryHeader),
				templates: Lists.newArrayList(template)
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
		1 * templateTitlesExtractingProcessorMock.process(Lists.newArrayList(template)) >> Lists.newArrayList(TEMPLATE_TITLE_1)
		1 * militaryConflictTemplateTemplatesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<List<String>, MilitaryConflictTemplate> enrichablePair ->
			enrichablePair.input == Lists.newArrayList(TEMPLATE_TITLE_1)
			enrichablePair.output == militaryConflictTemplate
		}
		0 * _
	}

}
