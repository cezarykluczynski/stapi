package com.cezarykluczynski.stapi.etl.template.episode.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.RawDatelinkExtractingProcessor
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate
import com.cezarykluczynski.stapi.etl.template.episode.provider.EpisodeFinalScriptDateFixedValueProvider
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageSection
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

import java.time.LocalDate

class EpisodeTemplateDatesEnrichingProcessorTest extends Specification {

	private static final String FIXED_TITLE = 'FIXED_TITLE'
	private static final String TITLE = 'TITLE'
	private static final String WIKITEXT = '* Final draft blah blah'
	private static final LocalDate FINAL_SCRIPT_DATE = LocalDate.of(1996, 4, 5)

	private RawDatelinkExtractingProcessor rawDatelinkExtractingProcessorMock

	private EpisodeFinalScriptDateFixedValueProvider episodeFinalScriptDateFixedValueProviderMock

	private EpisodeTemplateDatesEnrichingProcessor episodeTemplateDatesEnrichingProcessor

	void setup() {
		rawDatelinkExtractingProcessorMock = Mock()
		episodeFinalScriptDateFixedValueProviderMock = Mock()
		episodeTemplateDatesEnrichingProcessor = new EpisodeTemplateDatesEnrichingProcessor(
				rawDatelinkExtractingProcessorMock, episodeFinalScriptDateFixedValueProviderMock)
	}

	void "gets fixed date when it is present"() {
		given:
		EpisodeTemplate episodeTemplate = new EpisodeTemplate(
				title: FIXED_TITLE
		)

		when:
		episodeTemplateDatesEnrichingProcessor.enrich(EnrichablePair.of(new Page(), episodeTemplate))

		then:
		1 * episodeFinalScriptDateFixedValueProviderMock.getSearchedValue(FIXED_TITLE) >> FixedValueHolder.found(FINAL_SCRIPT_DATE)
		episodeTemplate.finalScriptDate == FINAL_SCRIPT_DATE
	}

	void "gets date from section when only one is present"() {
		given:
		Page page = new Page(
				sections: Lists.newArrayList(
						new PageSection(
								text: EpisodeTemplateDatesEnrichingProcessor.RELEVANT_PAGE_SECTIONS_NAMES[0],
								wikitext: WIKITEXT
						)
				)
		)
		EpisodeTemplate episodeTemplate = new EpisodeTemplate(
				title: TITLE
		)
		LocalDate localDate = LocalDate.of(2003, 10, 28)

		when:
		episodeTemplateDatesEnrichingProcessor.enrich(EnrichablePair.of(page, episodeTemplate))

		then:
		1 * episodeFinalScriptDateFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.notFound()
		1 * rawDatelinkExtractingProcessorMock.process(WIKITEXT) >> Lists.newArrayList(localDate)
		episodeTemplate.finalScriptDate == localDate
	}

	void "does not get date when there is more than one date"() {
		given:
		Page page = new Page(
				sections: Lists.newArrayList(
						new PageSection(
								text: EpisodeTemplateDatesEnrichingProcessor.RELEVANT_PAGE_SECTIONS_NAMES[0],
								wikitext: WIKITEXT
						)
				)
		)
		EpisodeTemplate episodeTemplate = new EpisodeTemplate(
				title: TITLE
		)
		LocalDate localDate1 = LocalDate.of(2003, 10, 28)
		LocalDate localDate2 = LocalDate.of(2004, 11, 29)

		when:
		episodeTemplateDatesEnrichingProcessor.enrich(EnrichablePair.of(page, episodeTemplate))

		then:
		1 * episodeFinalScriptDateFixedValueProviderMock.getSearchedValue(TITLE) >> FixedValueHolder.notFound()
		1 * rawDatelinkExtractingProcessorMock.process(WIKITEXT) >> Lists.newArrayList(localDate1, localDate2)
		episodeTemplate.finalScriptDate == null
	}

}
