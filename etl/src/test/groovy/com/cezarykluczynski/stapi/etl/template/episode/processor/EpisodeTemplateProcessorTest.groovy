package com.cezarykluczynski.stapi.etl.template.episode.processor

import com.cezarykluczynski.stapi.etl.template.common.dto.DayMonthYearCandidate
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DayMonthYearProcessor
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists

import java.time.LocalDate

class EpisodeTemplateProcessorTest extends AbstractEpisodeTemplateProcessorTest {

	private static final String SEASON_NUMBER_STRING = '3'
	private static final Integer SEASON_NUMBER_INTEGER = 3
	private static final String EPISODE_NUMBER_STRING = '8'
	private static final Integer EPISODE_NUMBER_INTEGER = 8
	private static final String PRODUCTION_SERIAL_NUMBER = '40511-721'
	private static final String PRODUCTION_SERIAL_NUMBER_ILL_FORMATTED =
			'{"comment":"<!-- Extra data production numbers = 401-402-->","content":"' + PRODUCTION_SERIAL_NUMBER + '"}'
	private static final String PRODUCTION_SERIAL_TOO_LONG_NUMBER = "Too long too long too long too long too long"
	private static final String AIRDATE_YEAR_STRING = "1998"
	private static final String AIRDATE_MONTH_STRING = "April"
	private static final String AIRDATE_DAY_STRING = "15"

	private DayMonthYearProcessor dayMonthYearProcessorMock

	private EpisodeTemplateStardateYearEnrichingProcessor episodeTemplateStardateYearEnrichingProcessorMock

	private EpisodeTemplateProcessor episodeTemplateProcessor

	def setup() {
		dayMonthYearProcessorMock = Mock(DayMonthYearProcessor)
		episodeTemplateStardateYearEnrichingProcessorMock = Mock(EpisodeTemplateStardateYearEnrichingProcessor)
		episodeTemplateProcessor = new EpisodeTemplateProcessor(dayMonthYearProcessorMock,
				episodeTemplateStardateYearEnrichingProcessorMock)
	}
	def "sets values from template parts"() {
		given:
		Template template = new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateProcessor.N_SEASON, SEASON_NUMBER_STRING),
						createTemplatePart(EpisodeTemplateProcessor.N_EPISODE, EPISODE_NUMBER_STRING),
						createTemplatePart(EpisodeTemplateProcessor.S_PRODUCTION_SERIAL_NUMBER, PRODUCTION_SERIAL_NUMBER),
						createTemplatePart(EpisodeTemplateProcessor.B_FEATURE_LENGTH, "1"),
						createTemplatePart(EpisodeTemplateProcessor.N_AIRDATE_YEAR, AIRDATE_YEAR_STRING),
						createTemplatePart(EpisodeTemplateProcessor.S_AIRDATE_MONTH, AIRDATE_MONTH_STRING),
						createTemplatePart(EpisodeTemplateProcessor.N_AIRDATE_DAY, AIRDATE_DAY_STRING)
				)
		)
		LocalDate usAirDate = LocalDate.of(1998, 4, 15)

		when:
		EpisodeTemplate episodeTemplate = episodeTemplateProcessor.process(template)

		then:
		1 * dayMonthYearProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			dayMonthYearCandidate.day == AIRDATE_DAY_STRING
			dayMonthYearCandidate.month == AIRDATE_MONTH_STRING
			dayMonthYearCandidate.year == AIRDATE_YEAR_STRING
			return usAirDate
		}
		1 * episodeTemplateStardateYearEnrichingProcessorMock.enrich(_)
		0 * _
		episodeTemplate.episodeStub instanceof Episode
		episodeTemplate.seasonNumber == SEASON_NUMBER_INTEGER
		episodeTemplate.episodeNumber == EPISODE_NUMBER_INTEGER
		episodeTemplate.productionSerialNumber == PRODUCTION_SERIAL_NUMBER
		episodeTemplate.featureLength
		episodeTemplate.usAirDate == usAirDate
	}

	def "tolerates ill-formated production serial number"() {
		given:
		Template template = new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateProcessor.S_PRODUCTION_SERIAL_NUMBER, PRODUCTION_SERIAL_NUMBER_ILL_FORMATTED),
				)
		)

		when:
		EpisodeTemplate episodeTemplate = episodeTemplateProcessor.process(template)

		then:
		episodeTemplate.productionSerialNumber == PRODUCTION_SERIAL_NUMBER
	}

	def "tolerates too long  production serial number"() {
		given:
		Template template = new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateProcessor.S_PRODUCTION_SERIAL_NUMBER, PRODUCTION_SERIAL_TOO_LONG_NUMBER),
				)
		)

		when:
		EpisodeTemplate episodeTemplate = episodeTemplateProcessor.process(template)

		then:
		episodeTemplate.productionSerialNumber == null
	}

	def "tolerates invalid or ill-formatted season numbers"() {
		when:
		EpisodeTemplate episodeTemplate = episodeTemplateProcessor.process(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateProcessor.N_EPISODE, "25/26")
				)
		))

		then:
		1 * episodeTemplateStardateYearEnrichingProcessorMock.enrich(_)
		0 * _
		episodeTemplate.episodeNumber == 25

		when:
		EpisodeTemplate episodeTemplate2 = episodeTemplateProcessor.process(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateProcessor.N_EPISODE, "NOT A NUMBER")
				)
		))

		then:
		1 * episodeTemplateStardateYearEnrichingProcessorMock.enrich(_)
		0 * _
		episodeTemplate2.episodeNumber == null
	}

}
