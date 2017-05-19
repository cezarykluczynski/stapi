package com.cezarykluczynski.stapi.etl.template.comics.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.service.PageSectionExtractor
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class ComicsTemplateCompositeEnrichingProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'

	private PageSectionExtractor pageSectionExtractorMock

	private ComicsTemplateWikitextStaffEnrichingProcessor comicsTemplateWikitextStaffEnrichingProcessorMock

	private ComicsTemplateCompositeEnrichingProcessor comicsTemplateCompositeEnrichingProcessor

	void setup() {
		pageSectionExtractorMock = Mock()
		comicsTemplateWikitextStaffEnrichingProcessorMock = Mock()
		comicsTemplateCompositeEnrichingProcessor = new ComicsTemplateCompositeEnrichingProcessor(pageSectionExtractorMock,
				comicsTemplateWikitextStaffEnrichingProcessorMock)
	}

	void "when creators sections is found, first section's wikitext is passed to ComicsTemplateWikitextStaffEnrichingProcessor"() {
		given:
		Page page = Mock()
		ComicsTemplate comicsTemplate = Mock()
		PageSection pageSection1 = new PageSection(wikitext: WIKITEXT)
		PageSection pageSection2 = new PageSection()

		when:
		comicsTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(page, comicsTemplate))

		then:
		1 * pageSectionExtractorMock.findByTitles(page, ComicsTemplateCompositeEnrichingProcessor.CREATORS,
				ComicsTemplateCompositeEnrichingProcessor.CREDITS) >> Lists.newArrayList(pageSection1, pageSection2)
		1 * comicsTemplateWikitextStaffEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<String, ComicsTemplate> enrichablePair ->
			assert enrichablePair.input == WIKITEXT
			assert enrichablePair.output == comicsTemplate
		}
		0 * _
	}

}
