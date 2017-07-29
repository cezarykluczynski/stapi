package com.cezarykluczynski.stapi.etl.template.episode.processor

import com.cezarykluczynski.stapi.etl.common.processor.AbstractTemplateProcessorTest
import com.cezarykluczynski.stapi.etl.common.processor.ImageTemplateStardateYearEnrichingProcessor
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DayMonthYearCandidate
import com.cezarykluczynski.stapi.etl.template.common.processor.ProductionSerialNumberProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DayMonthYearCandidateToLocalDateProcessor
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplateParameter
import com.cezarykluczynski.stapi.etl.util.constant.SeriesAbbreviation
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.season.repository.SeasonRepository
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
	private static final String SERIES_TITLE = 'SERIES_TITLE'

	private DayMonthYearCandidateToLocalDateProcessor dayMonthYearCandidateToLocalDateProcessorMock

	private ImageTemplateStardateYearEnrichingProcessor imageTemplateStardateYearEnrichingProcessorMock

	private ProductionSerialNumberProcessor productionSerialNumberProcessor

	private SeasonRepository seasonRepositoryMock

	private EpisodeTemplateProcessor episodeTemplateProcessor

	void setup() {
		dayMonthYearCandidateToLocalDateProcessorMock = Mock()
		imageTemplateStardateYearEnrichingProcessorMock = Mock()
		productionSerialNumberProcessor = Mock()
		seasonRepositoryMock = Mock()
		episodeTemplateProcessor = new EpisodeTemplateProcessor(dayMonthYearCandidateToLocalDateProcessorMock,
				imageTemplateStardateYearEnrichingProcessorMock, productionSerialNumberProcessor, seasonRepositoryMock)
	}
	void "sets values from template parts"() {
		given:
		Template template = new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateParameter.S_SERIES, SERIES_TITLE),
						createTemplatePart(EpisodeTemplateParameter.N_SEASON, SEASON_NUMBER_STRING),
						createTemplatePart(EpisodeTemplateParameter.N_EPISODE, EPISODE_NUMBER_STRING),
						createTemplatePart(EpisodeTemplateParameter.S_PRODUCTION_SERIAL_NUMBER, PRODUCTION_SERIAL_NUMBER_INPUT),
						createTemplatePart(EpisodeTemplateParameter.B_FEATURE_LENGTH, '1'),
						createTemplatePart(EpisodeTemplateParameter.N_AIRDATE_YEAR, AIRDATE_YEAR),
						createTemplatePart(EpisodeTemplateParameter.S_AIRDATE_MONTH, AIRDATE_MONTH),
						createTemplatePart(EpisodeTemplateParameter.N_AIRDATE_DAY, AIRDATE_DAY)
				)
		)
		LocalDate usAirDate = LocalDate.of(1998, 4, 15)
		Season season = Mock()

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
		1 * seasonRepositoryMock.findBySeriesAbbreviationAndSeasonNumber(SERIES_TITLE, SEASON_NUMBER_INTEGER) >> season
		0 * _
		episodeTemplate.episodeStub instanceof Episode
		episodeTemplate.seasonNumber == SEASON_NUMBER_INTEGER
		episodeTemplate.episodeNumber == EPISODE_NUMBER_INTEGER
		episodeTemplate.productionSerialNumber == PRODUCTION_SERIAL_NUMBER_OUTPUT
		episodeTemplate.featureLength
		episodeTemplate.usAirDate == usAirDate
		episodeTemplate.season == season
	}

	void "tolerates invalid or ill-formatted season numbers"() {
		given:
		Season season = Mock()

		when:
		EpisodeTemplate episodeTemplate = episodeTemplateProcessor.process(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateParameter.S_SERIES, SERIES_TITLE),
						createTemplatePart(EpisodeTemplateParameter.N_SEASON, SEASON_NUMBER_STRING),
						createTemplatePart(EpisodeTemplateParameter.N_EPISODE, '25/26')
				)
		))

		then:
		1 * imageTemplateStardateYearEnrichingProcessorMock.enrich(_)
		1 * seasonRepositoryMock.findBySeriesAbbreviationAndSeasonNumber(SERIES_TITLE, SEASON_NUMBER_INTEGER) >> season
		0 * _
		episodeTemplate.episodeNumber == 25

		when:
		EpisodeTemplate episodeTemplate2 = episodeTemplateProcessor.process(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateParameter.S_SERIES, SERIES_TITLE),
						createTemplatePart(EpisodeTemplateParameter.N_SEASON, SEASON_NUMBER_STRING),
						createTemplatePart(EpisodeTemplateParameter.N_EPISODE, 'NOT A NUMBER')
				)
		))

		then:
		1 * imageTemplateStardateYearEnrichingProcessorMock.enrich(_)
		1 * seasonRepositoryMock.findBySeriesAbbreviationAndSeasonNumber(SERIES_TITLE, SEASON_NUMBER_INTEGER) >> season
		0 * _
		episodeTemplate2.episodeNumber == null
	}

	void "fixes season number for 'The Cage'"() {
		given:
		Season season = Mock()

		when:
		EpisodeTemplate episodeTemplate = episodeTemplateProcessor.process(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateParameter.S_SERIES, SeriesAbbreviation.TOS),
						createTemplatePart(EpisodeTemplateParameter.N_SEASON, '0'),
						createTemplatePart(EpisodeTemplateParameter.N_EPISODE, '0')
				)
		))

		then:
		1 * imageTemplateStardateYearEnrichingProcessorMock.enrich(_)
		1 * seasonRepositoryMock.findBySeriesAbbreviationAndSeasonNumber(SeriesAbbreviation.TOS, 1) >> season
		0 * _
		episodeTemplate.episodeNumber == 0
	}

	void "tolerates template part with null key"() {
		given:
		Season season = Mock()

		when:
		episodeTemplateProcessor.process(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateParameter.S_SERIES, SERIES_TITLE),
						createTemplatePart(EpisodeTemplateParameter.N_SEASON, SEASON_NUMBER_STRING),
						createTemplatePart(null, null)
				)
		))

		then:
		1 * imageTemplateStardateYearEnrichingProcessorMock.enrich(_)
		1 * seasonRepositoryMock.findBySeriesAbbreviationAndSeasonNumber(SERIES_TITLE, SEASON_NUMBER_INTEGER) >> season
		0 * _
		notThrown(Exception)
	}

	void "throws exception when season is null"() {
		when:
		episodeTemplateProcessor.process(new Template(
				parts: Lists.newArrayList(

				)
		))

		then:
		1 * imageTemplateStardateYearEnrichingProcessorMock.enrich(_)
		0 * _
		thrown(NullPointerException)
	}

}
