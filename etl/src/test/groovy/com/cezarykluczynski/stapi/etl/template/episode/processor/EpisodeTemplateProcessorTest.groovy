package com.cezarykluczynski.stapi.etl.template.episode.processor

import com.cezarykluczynski.stapi.etl.common.processor.AbstractTemplateProcessorTest
import com.cezarykluczynski.stapi.etl.common.processor.ImageTemplateStardateYearEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYearCandidate
import com.cezarykluczynski.stapi.etl.template.common.processor.ProductionSerialNumberProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DayMonthYearCandidateToLocalDateProcessor
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists

import java.time.LocalDate

class EpisodeTemplateProcessorTest extends AbstractTemplateProcessorTest {

	private static final String PRODUCTION_SERIAL_NUMBER_INPUT = 'PRODUCTION_SERIAL_NUMBER_INPUT'
	private static final String PRODUCTION_SERIAL_NUMBER_OUTPUT = 'PRODUCTION_SERIAL_NUMBER_OUTPUT'
	private static final String SEASON_NUMBER_STRING = '3'
	private static final Integer SEASON_NUMBER_INTEGER = 3
	private static final String EPISODE_NUMBER_STRING = '8'
	private static final Integer EPISODE_NUMBER_INTEGER = 8
	private static final String AIRDATE_YEAR = '1998'
	private static final String AIRDATE_MONTH = 'April'
	private static final String AIRDATE_DAY = '15'

	private DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessorMock

	private ImageTemplateStardateYearEnrichingProcessor imageTemplateStardateYearEnrichingProcessorMock

	private ProductionSerialNumberProcessor productionSerialNumberProcessor

	private EpisodeTemplateProcessor episodeTemplateProcessor

	void setup() {
		dayMonthYearCandidateToLocalDateProcessorMock = Mock(DayMonthYearCandidateToLocalDateProcessor)
		imageTemplateStardateYearEnrichingProcessorMock = Mock(ImageTemplateStardateYearEnrichingProcessor)
		productionSerialNumberProcessor = Mock(ProductionSerialNumberProcessor)
		episodeTemplateProcessor = new EpisodeTemplateProcessor(dayMonthYearCandidateToLocalDateProcessorMock,
				imageTemplateStardateYearEnrichingProcessorMock, productionSerialNumberProcessor)
	}
	void "sets values from template parts"() {
		given:
		Template template = new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateProcessor.N_SEASON, SEASON_NUMBER_STRING),
						createTemplatePart(EpisodeTemplateProcessor.N_EPISODE, EPISODE_NUMBER_STRING),
						createTemplatePart(EpisodeTemplateProcessor.S_PRODUCTION_SERIAL_NUMBER, PRODUCTION_SERIAL_NUMBER_INPUT),
						createTemplatePart(EpisodeTemplateProcessor.B_FEATURE_LENGTH, '1'),
						createTemplatePart(EpisodeTemplateProcessor.N_AIRDATE_YEAR, AIRDATE_YEAR),
						createTemplatePart(EpisodeTemplateProcessor.S_AIRDATE_MONTH, AIRDATE_MONTH),
						createTemplatePart(EpisodeTemplateProcessor.N_AIRDATE_DAY, AIRDATE_DAY)
				)
		)
		LocalDate usAirDate = LocalDate.of(1998, 4, 15)

		when:
		EpisodeTemplate episodeTemplate = episodeTemplateProcessor.process(template)

		then:
		1 * dayMonthYearCandidateToLocalDateProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			dayMonthYearCandidate.day == AIRDATE_DAY
			dayMonthYearCandidate.month == AIRDATE_MONTH
			dayMonthYearCandidate.year == AIRDATE_YEAR
			usAirDate
		}
		1 * productionSerialNumberProcessor.process(PRODUCTION_SERIAL_NUMBER_INPUT) >> PRODUCTION_SERIAL_NUMBER_OUTPUT
		1 * imageTemplateStardateYearEnrichingProcessorMock.enrich(_)
		0 * _
		episodeTemplate.episodeStub instanceof Episode
		episodeTemplate.seasonNumber == SEASON_NUMBER_INTEGER
		episodeTemplate.episodeNumber == EPISODE_NUMBER_INTEGER
		episodeTemplate.productionSerialNumber == PRODUCTION_SERIAL_NUMBER_OUTPUT
		episodeTemplate.featureLength
		episodeTemplate.usAirDate == usAirDate
	}

	void "tolerates invalid or ill-formatted season numbers"() {
		when:
		EpisodeTemplate episodeTemplate = episodeTemplateProcessor.process(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateProcessor.N_EPISODE, '25/26')
				)
		))

		then:
		1 * imageTemplateStardateYearEnrichingProcessorMock.enrich(_)
		0 * _
		episodeTemplate.episodeNumber == 25

		when:
		EpisodeTemplate episodeTemplate2 = episodeTemplateProcessor.process(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateProcessor.N_EPISODE, 'NOT A NUMBER')
				)
		))

		then:
		1 * imageTemplateStardateYearEnrichingProcessorMock.enrich(_)
		0 * _
		episodeTemplate2.episodeNumber == null
	}

	void "tolerates template part with null key"() {
		when:
		episodeTemplateProcessor.process(new Template(
				parts: Lists.newArrayList(createTemplatePart(null, null))
		))

		then:
		1 * imageTemplateStardateYearEnrichingProcessorMock.enrich(_)
		0 * _
		notThrown(Exception)
	}

}
