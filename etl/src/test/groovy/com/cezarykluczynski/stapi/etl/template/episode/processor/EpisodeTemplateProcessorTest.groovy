package com.cezarykluczynski.stapi.etl.template.episode.processor

import com.cezarykluczynski.stapi.etl.template.common.dto.DayMonthYearCandidate
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DayMonthYearProcessor
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

import java.time.LocalDate

class EpisodeTemplateProcessorTest extends Specification {

	private static final String SEASON_NUMBER_STRING = '3'
	private static final Integer SEASON_NUMBER_INTEGER = 3
	private static final String EPISODE_NUMBER_STRING = '8'
	private static final Integer EPISODE_NUMBER_INTEGER = 8
	private static final String PRODUCTION_SERIAL_NUMBER = 'PRODUCTION_SERIAL_NUMBER'
	private static final String YEAR_BARE_STRING = '2360'
	private static final String YEAR_TOO_EARLY_BARE_STRING = "999"
	private static final String YEAR_TOO_LATE_BARE_STRING = "10000"
	private static final String YEAR_STRING = "([[${YEAR_BARE_STRING}]])"
	private static final String YEAR_TOO_EARLY_STRING = "([[${YEAR_TOO_EARLY_BARE_STRING}]])"
	private static final String YEAR_TOO_LATE_STRING = "([[${YEAR_TOO_LATE_BARE_STRING}]])"
	private static final String YEAR_INVALID_STRING = "([[${INVALID}]])"
	private static final Integer YEAR_INTEGER = 2360
	private static final Float STARDATE = (Float) 123.4
	private static final String WS_DATE = "${STARDATE} ${YEAR_STRING}"
	private static final String INVALID = 'INVALID'
	private static final String WS_DATE_INVALID_1 = "${STARDATE} ${INVALID}"
	private static final String WS_DATE_INVALID_2 = "${INVALID} ${YEAR_STRING}"
	private static final String WS_DATE_INVALID_3 = "${STARDATE} ${YEAR_STRING} ${INVALID}"
	private static final String WS_DATE_INVALID_4 = "${STARDATE} ${YEAR_TOO_EARLY_STRING}"
	private static final String WS_DATE_INVALID_5 = "${STARDATE} ${YEAR_TOO_LATE_STRING}"
	private static final String WS_DATE_INVALID_6 = "${STARDATE} ${YEAR_INVALID_STRING}"
	private static final String AIRDATE_YEAR_STRING = "1998"
	private static final String AIRDATE_MONTH_STRING = "April"
	private static final String AIRDATE_DAY_STRING = "15"

	private WikitextApi wikitextApiMock

	private DayMonthYearProcessor dayMonthYearProcessorMock

	private EpisodeTemplateProcessor episodeTemplateProcessor

	def setup() {
		wikitextApiMock = Mock(WikitextApi)
		dayMonthYearProcessorMock = Mock(DayMonthYearProcessor)
		episodeTemplateProcessor = new EpisodeTemplateProcessor(wikitextApiMock, dayMonthYearProcessorMock)
	}
	def "sets values from template parts"() {
		given:
		Template template = new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateProcessor.N_SEASON, SEASON_NUMBER_STRING),
						createTemplatePart(EpisodeTemplateProcessor.N_EPISODE, EPISODE_NUMBER_STRING),
						createTemplatePart(EpisodeTemplateProcessor.S_PRODUCTION_SERIAL_NUMBER, PRODUCTION_SERIAL_NUMBER),
						createTemplatePart(EpisodeTemplateProcessor.B_FEATURE_LENGTH, "1"),
						createTemplatePart(EpisodeTemplateProcessor.WS_DATE, WS_DATE),
						createTemplatePart(EpisodeTemplateProcessor.N_AIRDATE_YEAR, AIRDATE_YEAR_STRING),
						createTemplatePart(EpisodeTemplateProcessor.S_AIRDATE_MONTH, AIRDATE_MONTH_STRING),
						createTemplatePart(EpisodeTemplateProcessor.N_AIRDATE_DAY, AIRDATE_DAY_STRING)
				)
		)
		LocalDate usAirDate = LocalDate.of(1998, 4, 15)

		when:
		EpisodeTemplate episodeTemplate = episodeTemplateProcessor.process(template)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(YEAR_STRING) >> Lists.newArrayList(
				new PageLink(
						title: YEAR_BARE_STRING
				)
		)
		1 * dayMonthYearProcessorMock.process(_ as DayMonthYearCandidate) >> { DayMonthYearCandidate dayMonthYearCandidate ->
			dayMonthYearCandidate.day == AIRDATE_DAY_STRING
			dayMonthYearCandidate.month == AIRDATE_MONTH_STRING
			dayMonthYearCandidate.year == AIRDATE_YEAR_STRING
			return usAirDate
		}
		episodeTemplate.episodeStub instanceof Episode
		episodeTemplate.seasonNumber == SEASON_NUMBER_INTEGER
		episodeTemplate.episodeNumber == EPISODE_NUMBER_INTEGER
		episodeTemplate.productionSerialNumber == PRODUCTION_SERIAL_NUMBER
		episodeTemplate.featureLength
		episodeTemplate.stardate == STARDATE
		episodeTemplate.year == YEAR_INTEGER
		episodeTemplate.usAirDate == usAirDate
	}

	def "tolerates invalid dates"() {
		when:
		EpisodeTemplate episodeTemplate1 = episodeTemplateProcessor.process(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateProcessor.WS_DATE, WS_DATE_INVALID_1)
				)
		))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(INVALID) >> Lists.newArrayList()
		episodeTemplate1.stardate == STARDATE
		episodeTemplate1.year == null

		when:
		EpisodeTemplate episodeTemplate2 = episodeTemplateProcessor.process(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateProcessor.WS_DATE, WS_DATE_INVALID_2)
				)
		))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(YEAR_STRING) >> Lists.newArrayList(
				new PageLink(
						title: YEAR_BARE_STRING
				)
		)
		episodeTemplate2.stardate == null
		episodeTemplate2.year == YEAR_INTEGER

		when:
		EpisodeTemplate episodeTemplate3 = episodeTemplateProcessor.process(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateProcessor.WS_DATE, WS_DATE_INVALID_3)
				)
		))

		then:
		0 * _
		episodeTemplate3.stardate == null
		episodeTemplate3.year == null

		when:
		EpisodeTemplate episodeTemplate4 = episodeTemplateProcessor.process(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateProcessor.WS_DATE, WS_DATE_INVALID_4)
				)
		))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(YEAR_TOO_EARLY_STRING) >> Lists.newArrayList(
				new PageLink(
						title: YEAR_TOO_EARLY_BARE_STRING
				)
		)
		0 * _
		episodeTemplate4.stardate == STARDATE
		episodeTemplate4.year == null

		when:
		EpisodeTemplate episodeTemplate5 = episodeTemplateProcessor.process(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateProcessor.WS_DATE, WS_DATE_INVALID_5)
				)
		))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(YEAR_TOO_LATE_STRING) >> Lists.newArrayList(
				new PageLink(
						title: YEAR_TOO_LATE_BARE_STRING
				)
		)
		0 * _
		episodeTemplate5.stardate == STARDATE
		episodeTemplate5.year == null

		when:
		EpisodeTemplate episodeTemplate6 = episodeTemplateProcessor.process(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateProcessor.WS_DATE, WS_DATE_INVALID_6)
				)
		))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(YEAR_INVALID_STRING) >> Lists.newArrayList(
				new PageLink(
						title: INVALID
				)
		)
		0 * _
		episodeTemplate6.stardate == STARDATE
		episodeTemplate6.year == null
	}

	private static Template.Part createTemplatePart(String key, String value) {
		return new Template.Part(
				key: key,
				value: value
		)
	}

}
