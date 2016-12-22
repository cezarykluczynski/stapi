package com.cezarykluczynski.stapi.etl.template.episode.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.template.episode.dto.EpisodeTemplate
import com.cezarykluczynski.stapi.etl.template.episode.dto.StardateYearDTO
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists

class EpisodeTemplateStardateYearEnrichingProcessorTest extends AbstractEpisodeTemplateProcessorTest {

	private static final String TITLE = 'TITLE'
	private static final String YEAR_BARE_STRING_FROM = '2368'
	private static final String YEAR_BARE_STRING_TO = '2369'
	private static final String YEAR_TOO_EARLY_BARE_STRING = "999"
	private static final String YEAR_TOO_LATE_BARE_STRING = "10000"
	private static final String YEAR_STRING_FROM = "([[${YEAR_BARE_STRING_FROM}]])"
	private static final String YEAR_STRING_FROM_TO = "([[${YEAR_BARE_STRING_FROM}]]-[[${YEAR_BARE_STRING_TO}]])"
	private static final String YEAR_STRING_TO_FROM = "([[${YEAR_BARE_STRING_TO}]]-[[${YEAR_BARE_STRING_FROM}]])"
	private static final String YEAR_TOO_EARLY_STRING = "([[${YEAR_TOO_EARLY_BARE_STRING}]])"
	private static final String YEAR_TOO_LATE_STRING = "([[${YEAR_TOO_LATE_BARE_STRING}]])"
	private static final String YEAR_INVALID_STRING = "([[${INVALID}]])"
	private static final Integer YEAR_INTEGER_FROM = 2368
	private static final Integer YEAR_INTEGER_TO = 2369
	private static final Float STARDATE_FROM = 123.4F
	private static final Float STARDATE_TO = 234.5F
	private static final String WS_DATE = "${STARDATE_FROM} ${YEAR_STRING_FROM}"
	private static final String WS_DATE_FROM_TO = "${STARDATE_FROM}-${STARDATE_TO} ${YEAR_STRING_FROM_TO}"
	private static final String WS_DATE_TO_FROM = "${STARDATE_TO}-${STARDATE_FROM} ${YEAR_STRING_TO_FROM}"
	private static final String INVALID = 'INVALID'
	private static final String WS_DATE_INVALID_1 = "${STARDATE_FROM} ${INVALID}"
	private static final String WS_DATE_INVALID_2 = "${INVALID} ${YEAR_STRING_FROM}"
	private static final String WS_DATE_INVALID_3 = "${STARDATE_FROM} ${YEAR_STRING_FROM} ${INVALID}"
	private static final String WS_DATE_INVALID_4 = "${STARDATE_FROM} ${YEAR_TOO_EARLY_STRING}"
	private static final String WS_DATE_INVALID_5 = "${STARDATE_FROM} ${YEAR_TOO_LATE_STRING}"
	private static final String WS_DATE_INVALID_6 = "${STARDATE_FROM} ${YEAR_INVALID_STRING}"
	private static final String WS_DATE_INVALID_7 = "${STARDATE_FROM}-${INVALID} ${INVALID}"

	private WikitextApi wikitextApiMock

	private EpisodeTemplateStardateYearFixedValueProvider episodeTemplateStardateYearFixedValueProviderMock

	private EpisodeTemplateStardateYearEnrichingProcessor episodeTemplateStardateYearEnrichingProcessor

	def setup() {
		wikitextApiMock = Mock(WikitextApi)
		episodeTemplateStardateYearFixedValueProviderMock = Mock(EpisodeTemplateStardateYearFixedValueProvider)
		episodeTemplateStardateYearEnrichingProcessor = new EpisodeTemplateStardateYearEnrichingProcessor(
				wikitextApiMock, episodeTemplateStardateYearFixedValueProviderMock)
	}

	def "sets values from template part"() {
		given:
		Template template = new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateStardateYearEnrichingProcessor.WS_DATE, WS_DATE),
				)
		)
		EpisodeTemplate episodeTemplate = new EpisodeTemplate()

		when:
		episodeTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(template, episodeTemplate))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(YEAR_STRING_FROM) >> Lists.newArrayList(
				new PageLink(
						title: YEAR_BARE_STRING_FROM
				)
		)
		episodeTemplate.stardateFrom == STARDATE_FROM
		episodeTemplate.stardateTo == STARDATE_FROM
		episodeTemplate.yearFrom == YEAR_INTEGER_FROM
		episodeTemplate.yearTo == YEAR_INTEGER_FROM
	}

	def "sets stardates and years from and to values from template part"() {
		given:
		Template template = new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateStardateYearEnrichingProcessor.WS_DATE, WS_DATE_FROM_TO),
				)
		)
		EpisodeTemplate episodeTemplate = new EpisodeTemplate()

		when:
		episodeTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(template, episodeTemplate))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(YEAR_STRING_FROM_TO) >> Lists.newArrayList(
				new PageLink(
						title: YEAR_BARE_STRING_FROM
				),
				new PageLink(
						title: YEAR_BARE_STRING_TO
				)
		)
		episodeTemplate.stardateFrom == STARDATE_FROM
		episodeTemplate.stardateTo == STARDATE_TO
		episodeTemplate.yearFrom == YEAR_INTEGER_FROM
		episodeTemplate.yearTo == YEAR_INTEGER_TO
	}

	def "sets stardates and years from and to values from template part, then reverse values so the lower if always 'from'"() {
		given:
		Template template = new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateStardateYearEnrichingProcessor.WS_DATE, WS_DATE_TO_FROM),
				)
		)
		EpisodeTemplate episodeTemplate = new EpisodeTemplate()

		when:
		episodeTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(template, episodeTemplate))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(YEAR_STRING_TO_FROM) >> Lists.newArrayList(
				new PageLink(
						title: YEAR_BARE_STRING_TO
				),
				new PageLink(
						title: YEAR_BARE_STRING_FROM
				)
		)
		episodeTemplate.stardateFrom == STARDATE_FROM
		episodeTemplate.stardateTo == STARDATE_TO
		episodeTemplate.yearFrom == YEAR_INTEGER_FROM
		episodeTemplate.yearTo == YEAR_INTEGER_TO
	}

	def "tolerates invalid dates"() {
		when:
		EpisodeTemplate episodeTemplate1 = new EpisodeTemplate()
		episodeTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateStardateYearEnrichingProcessor.WS_DATE, WS_DATE_INVALID_1)
				)
		), episodeTemplate1))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(INVALID) >> Lists.newArrayList()
		episodeTemplate1.stardateFrom == STARDATE_FROM
		episodeTemplate1.stardateTo == STARDATE_FROM
		episodeTemplate1.yearFrom == null
		episodeTemplate1.yearTo == null

		when:
		EpisodeTemplate episodeTemplate2 = new EpisodeTemplate()
		episodeTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateStardateYearEnrichingProcessor.WS_DATE, WS_DATE_INVALID_2)
				)
		), episodeTemplate2))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(YEAR_STRING_FROM) >> Lists.newArrayList(
				new PageLink(
						title: YEAR_BARE_STRING_FROM
				)
		)
		episodeTemplate2.stardateFrom == null
		episodeTemplate2.stardateTo == null
		episodeTemplate2.yearFrom == YEAR_INTEGER_FROM
		episodeTemplate2.yearTo == YEAR_INTEGER_FROM

		when:
		EpisodeTemplate episodeTemplate3 = new EpisodeTemplate()
		episodeTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateStardateYearEnrichingProcessor.WS_DATE, WS_DATE_INVALID_3)
				)
		), episodeTemplate3))

		then:
		0 * _
		episodeTemplate3.stardateFrom == null
		episodeTemplate3.stardateTo == null
		episodeTemplate3.yearFrom == null
		episodeTemplate3.yearTo == null

		when:
		EpisodeTemplate episodeTemplate4 = new EpisodeTemplate()
		episodeTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateStardateYearEnrichingProcessor.WS_DATE, WS_DATE_INVALID_4)
				)
		), episodeTemplate4))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(YEAR_TOO_EARLY_STRING) >> Lists.newArrayList(
				new PageLink(
						title: YEAR_TOO_EARLY_BARE_STRING
				)
		)
		0 * _
		episodeTemplate4.stardateFrom == STARDATE_FROM
		episodeTemplate4.stardateTo == STARDATE_FROM
		episodeTemplate4.yearFrom == null
		episodeTemplate4.yearTo == null

		when:
		EpisodeTemplate episodeTemplate5 = new EpisodeTemplate()
		episodeTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateStardateYearEnrichingProcessor.WS_DATE, WS_DATE_INVALID_5)
				)
		), episodeTemplate5))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(YEAR_TOO_LATE_STRING) >> Lists.newArrayList(
				new PageLink(
						title: YEAR_TOO_LATE_BARE_STRING
				)
		)
		0 * _
		episodeTemplate5.stardateFrom == STARDATE_FROM
		episodeTemplate5.stardateTo == STARDATE_FROM
		episodeTemplate5.yearFrom == null
		episodeTemplate5.yearTo == null

		when:
		EpisodeTemplate episodeTemplate6 = new EpisodeTemplate()
		episodeTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateStardateYearEnrichingProcessor.WS_DATE, WS_DATE_INVALID_6)
				)
		), episodeTemplate6))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(YEAR_INVALID_STRING) >> Lists.newArrayList(
				new PageLink(
						title: INVALID
				)
		)
		0 * _
		episodeTemplate6.stardateFrom == STARDATE_FROM
		episodeTemplate6.stardateTo == STARDATE_FROM
		episodeTemplate6.yearFrom == null
		episodeTemplate6.yearTo == null

		when:
		EpisodeTemplate episodeTemplate7 = new EpisodeTemplate()
		episodeTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateStardateYearEnrichingProcessor.WS_DATE, null)
				)
		), episodeTemplate7))

		then:
		0 * _
		episodeTemplate7.stardateFrom == null
		episodeTemplate7.stardateTo == null
		episodeTemplate7.yearFrom == null
		episodeTemplate7.yearTo == null

		when:
		EpisodeTemplate episodeTemplate8 = new EpisodeTemplate()
		episodeTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateStardateYearEnrichingProcessor.WS_DATE, WS_DATE_INVALID_7)
				)
		), episodeTemplate8))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(INVALID) >> Lists.newArrayList()
		0 * _
		episodeTemplate8.stardateFrom == STARDATE_FROM
		episodeTemplate8.stardateTo == STARDATE_FROM
		episodeTemplate8.yearFrom == null
		episodeTemplate8.yearTo == null
	}

	def "when title is found, and then FixedValueHolder holds value"() {
		given:
		EpisodeTemplate episodeTemplate = new EpisodeTemplate()
		StardateYearDTO stardateYear = new StardateYearDTO(STARDATE_FROM, STARDATE_TO, YEAR_INTEGER_FROM, YEAR_INTEGER_TO)
		FixedValueHolder<StardateYearDTO> stardateYearFixedValueHolder = FixedValueHolder.found(stardateYear)

		when:
		episodeTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateStardateYearEnrichingProcessor.S_TITLE, TITLE),
						createTemplatePart(EpisodeTemplateStardateYearEnrichingProcessor.WS_DATE, WS_DATE_INVALID_1)
				)
		), episodeTemplate))

		then: 'EpisodeTemplateStardateYearFixedValueProvider is used to retrieve FixedValueHolder'
		1 * episodeTemplateStardateYearFixedValueProviderMock.getSearchedValue(TITLE) >> stardateYearFixedValueHolder

		then: 'values are set when value is found'
		episodeTemplate.stardateFrom == STARDATE_FROM
		episodeTemplate.stardateTo == STARDATE_TO
		episodeTemplate.yearFrom == YEAR_INTEGER_FROM
		episodeTemplate.yearTo == YEAR_INTEGER_TO

		then: 'no other interactions are expected'
		0 * _
	}

	def "when title is found, and then FixedValueHolder does not hold value"() {
		given:
		EpisodeTemplate episodeTemplate = new EpisodeTemplate()
		FixedValueHolder<StardateYearDTO> stardateYearFixedValueHolder = FixedValueHolder.notFound()

		when:
		episodeTemplateStardateYearEnrichingProcessor.enrich(EnrichablePair.of(new Template(
				parts: Lists.newArrayList(
						createTemplatePart(EpisodeTemplateStardateYearEnrichingProcessor.S_TITLE, TITLE),
						createTemplatePart(EpisodeTemplateStardateYearEnrichingProcessor.WS_DATE, WS_DATE)
				)
		), episodeTemplate))

		then: 'EpisodeTemplateStardateYearFixedValueProvider is used to retrieve FixedValueHolder'
		1 * episodeTemplateStardateYearFixedValueProviderMock.getSearchedValue(TITLE) >> stardateYearFixedValueHolder

		then: 'values are not set'
		episodeTemplate.stardateFrom == STARDATE_FROM
		episodeTemplate.stardateTo == STARDATE_FROM
		episodeTemplate.yearFrom == null
		episodeTemplate.yearTo == null

		then: 'other interactions are expected'
		1 * wikitextApiMock.getPageLinksFromWikitext(_) >> Lists.newArrayList()
		0 * _
	}

}
