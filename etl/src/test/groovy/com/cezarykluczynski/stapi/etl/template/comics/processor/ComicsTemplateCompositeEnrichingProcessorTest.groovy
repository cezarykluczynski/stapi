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
	private static final String SUBSECTION_1_WIKITEXT = 'SUBSECTION_1_WIKITEXT'
	private static final String SUBSECTION_2_WIKITEXT = 'SUBSECTION_2_WIKITEXT'

	private PageSectionExtractor pageSectionExtractorMock

	private ComicsTemplateWikitextStaffEnrichingProcessor comicsTemplateWikitextStaffEnrichingProcessorMock

	private ComicsTemplateWikitextCharactersEnrichingProcessor comicsTemplateWikitextCharactersEnrichingProcessorMock

	private ComicsTemplateCompositeEnrichingProcessor comicsTemplateCompositeEnrichingProcessor

	void setup() {
		pageSectionExtractorMock = Mock()
		comicsTemplateWikitextStaffEnrichingProcessorMock = Mock()
		comicsTemplateWikitextCharactersEnrichingProcessorMock = Mock()
		comicsTemplateCompositeEnrichingProcessor = new ComicsTemplateCompositeEnrichingProcessor(pageSectionExtractorMock,
				comicsTemplateWikitextStaffEnrichingProcessorMock, comicsTemplateWikitextCharactersEnrichingProcessorMock)
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
		1 * pageSectionExtractorMock.findByTitlesIncludingSubsections(page, ComicsTemplateCompositeEnrichingProcessor.CHARACTERS) >> Lists.newArrayList()
		1 * comicsTemplateWikitextStaffEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<String, ComicsTemplate> enrichablePair ->
			assert enrichablePair.input == WIKITEXT
			assert enrichablePair.output == comicsTemplate
		}
		0 * _
	}

	@SuppressWarnings('BracesForMethod')
	void """when characters sections is found, first section's wikitext alongs with subsection is passed
			to ComicsTemplateWikitextStaffEnrichingProcessor"""() {
		given:
		Page page = Mock()
		ComicsTemplate comicsTemplate = Mock()
		PageSection pageSection1 = new PageSection(wikitext: WIKITEXT)
		PageSection pageSection2 = new PageSection(wikitext: SUBSECTION_1_WIKITEXT)
		PageSection pageSection3 = new PageSection(wikitext: SUBSECTION_2_WIKITEXT)

		when:
		comicsTemplateCompositeEnrichingProcessor.enrich(EnrichablePair.of(page, comicsTemplate))

		then:
		1 * pageSectionExtractorMock.findByTitles(page, ComicsTemplateCompositeEnrichingProcessor.CREATORS,
				ComicsTemplateCompositeEnrichingProcessor.CREDITS) >> Lists.newArrayList()
		1 * pageSectionExtractorMock.findByTitlesIncludingSubsections(page, ComicsTemplateCompositeEnrichingProcessor.CHARACTERS) >> Lists
				.newArrayList(pageSection1, pageSection2, pageSection3)
		1 * comicsTemplateWikitextCharactersEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
			EnrichablePair<String, ComicsTemplate> enrichablePair ->
				assert enrichablePair.input == "${WIKITEXT}\n${SUBSECTION_1_WIKITEXT}\n${SUBSECTION_2_WIKITEXT}".toString()
				assert enrichablePair.output == comicsTemplate
		}
		0 * _
	}

}
