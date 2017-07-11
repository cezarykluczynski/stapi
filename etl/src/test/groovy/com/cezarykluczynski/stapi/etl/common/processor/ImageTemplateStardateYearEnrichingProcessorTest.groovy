package com.cezarykluczynski.stapi.etl.common.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.template.common.dto.ImageTemplate
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateYearDTO
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.enums.StardateYearSource
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.StardateYearCandidateDTO
import com.cezarykluczynski.stapi.etl.template.common.processor.StardateYearProcessor
import com.cezarykluczynski.stapi.etl.template.episode.processor.EpisodeTemplateStardateYearFixedValueProvider
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists

class ImageTemplateStardateYearEnrichingProcessorTest extends AbstractTemplateProcessorTest {

	private static final String TITLE = 'TITLE'
	private static final Integer YEAR_INTEGER_FROM = 2368
	private static final Integer YEAR_INTEGER_TO = 2369
	private static final Float STARDATE_FROM = 123.4F
	private static final Float STARDATE_TO = 234.5F
	private static final String WS_DATE = 'WS_DATE'
	private static final String INVALID = 'INVALID'
	private static final String WS_DATE_INVALID_1 = "${STARDATE_FROM} ${INVALID}"

	private EpisodeTemplateStardateYearFixedValueProvider episodeTemplateStardateYearFixedValueProviderMock

	private StardateYearProcessor stardateYearProcessorMock

	private ImageTemplateStardateYearEnrichingProcessor imageTemplateStardateYearEnrichingProcessor

	void setup() {
		episodeTemplateStardateYearFixedValueProviderMock = Mock()
		stardateYearProcessorMock = Mock()
		imageTemplateStardateYearEnrichingProcessor = new ImageTemplateStardateYearEnrichingProcessor(
				episodeTemplateStardateYearFixedValueProviderMock, stardateYearProcessorMock)
	}

	void "when title is not found, values is set from StardateYearProcessor"() {
		given:
		Template template = new Template(
				parts: Lists.newArrayList(
						createTemplatePart(ImageTemplateStardateYearEnrichingProcessor.WS_DATE, WS_DATE),
				)
		)
		ImageTemplate imageTemplate = new ImageTemplate()
		StardateYearDTO stardateYearDTO = new StardateYearDTO(
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_INTEGER_FROM,
				yearTo: YEAR_INTEGER_TO)

		when:
		imageTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(template, imageTemplate))

		then:
		1 * stardateYearProcessorMock.process(StardateYearCandidateDTO.of(WS_DATE, null, StardateYearSource.EPISODE)) >> stardateYearDTO
		imageTemplate.stardateFrom == STARDATE_FROM
		imageTemplate.stardateTo == STARDATE_TO
		imageTemplate.yearFrom == YEAR_INTEGER_FROM
		imageTemplate.yearTo == YEAR_INTEGER_TO
	}

	void "when title is found, and then FixedValueHolder holds value"() {
		given:
		ImageTemplate imageTemplate = new ImageTemplate()
		StardateYearDTO stardateYear = new StardateYearDTO(STARDATE_FROM, STARDATE_TO, YEAR_INTEGER_FROM, YEAR_INTEGER_TO)
		FixedValueHolder<StardateYearDTO> stardateYearFixedValueHolder = FixedValueHolder.found(stardateYear)

		when:
		imageTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(ImageTemplateStardateYearEnrichingProcessor.S_TITLE, TITLE),
						createTemplatePart(ImageTemplateStardateYearEnrichingProcessor.WS_DATE, WS_DATE_INVALID_1)
				)
		), imageTemplate))

		then: 'EpisodeTemplateStardateYearFixedValueProvider is used to retrieve FixedValueHolder'
		1 * episodeTemplateStardateYearFixedValueProviderMock.getSearchedValue(TITLE) >> stardateYearFixedValueHolder

		then: 'values are set when value is found'
		imageTemplate.stardateFrom == STARDATE_FROM
		imageTemplate.stardateTo == STARDATE_TO
		imageTemplate.yearFrom == YEAR_INTEGER_FROM
		imageTemplate.yearTo == YEAR_INTEGER_TO

		then: 'no other interactions are expected'
		0 * _
	}

	void "when title is found, and then FixedValueHolder does not hold value"() {
		given:
		ImageTemplate imageTemplate = new ImageTemplate()
		FixedValueHolder<StardateYearDTO> stardateYearFixedValueHolder = FixedValueHolder.notFound()
		StardateYearDTO stardateYearDTO = new StardateYearDTO(
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_INTEGER_FROM,
				yearTo: YEAR_INTEGER_TO)

		when:
		imageTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(ImageTemplateStardateYearEnrichingProcessor.S_TITLE, TITLE),
						createTemplatePart(ImageTemplateStardateYearEnrichingProcessor.WS_DATE, WS_DATE)
				)
		), imageTemplate))

		then: 'EpisodeTemplateStardateYearFixedValueProvider is used to retrieve FixedValueHolder'
		1 * episodeTemplateStardateYearFixedValueProviderMock.getSearchedValue(TITLE) >> stardateYearFixedValueHolder

		then: 'values are not set'
		imageTemplate.stardateFrom == STARDATE_FROM
		imageTemplate.stardateTo == STARDATE_TO
		imageTemplate.yearFrom == YEAR_INTEGER_FROM
		imageTemplate.yearTo == YEAR_INTEGER_TO

		then: 'interaction with StardateYearProcessor is expected'
		1 * stardateYearProcessorMock.process(StardateYearCandidateDTO.of(WS_DATE, TITLE, StardateYearSource.EPISODE)) >> stardateYearDTO
		0 * _
	}

	void "tolerates template part with null key"() {
		given:
		Template template = new Template(
				parts: Lists.newArrayList(createTemplatePart(null, WS_DATE),)
		)
		ImageTemplate imageTemplate = new ImageTemplate()

		when:
		imageTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(template, imageTemplate))

		then:
		notThrown(Exception)
	}

}
