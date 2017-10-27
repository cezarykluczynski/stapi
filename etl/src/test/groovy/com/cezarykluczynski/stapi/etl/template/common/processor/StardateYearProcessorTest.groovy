package com.cezarykluczynski.stapi.etl.template.common.processor

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.StardateYearDTO
import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.enums.StardateYearSource
import com.cezarykluczynski.stapi.etl.template.common.dto.performance.StardateYearCandidateDTO
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.google.common.collect.Lists
import spock.lang.Specification

class StardateYearProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String YEAR_BARE_STRING_FROM = '2368'
	private static final String YEAR_BARE_STRING_TO = '2369'
	private static final String YEAR_TOO_EARLY_BARE_STRING = '999'
	private static final String YEAR_TOO_LATE_BARE_STRING = '10000'
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

	private StardateYearProcessor stardateYearProcessor

	void setup() {
		wikitextApiMock = Mock()
		stardateYearProcessor = new StardateYearProcessor(wikitextApiMock)
	}

	void "sets stardates and years from and to values"() {
		when:
		StardateYearDTO stardateYearDTO = stardateYearProcessor
				.process(StardateYearCandidateDTO.of(WS_DATE_FROM_TO, TITLE, StardateYearSource.EPISODE))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(YEAR_STRING_FROM_TO) >> Lists.newArrayList(
				new PageLink(
						title: YEAR_BARE_STRING_FROM
				),
				new PageLink(
						title: YEAR_BARE_STRING_TO
				)
		)
		stardateYearDTO.stardateFrom == STARDATE_FROM
		stardateYearDTO.stardateTo == STARDATE_TO
		stardateYearDTO.yearFrom == YEAR_INTEGER_FROM
		stardateYearDTO.yearTo == YEAR_INTEGER_TO
	}

	void "sets stardates and years from and to values, then reverse values so the lower if always 'from'"() {
		when:
		StardateYearDTO stardateYearDTO = stardateYearProcessor
				.process(StardateYearCandidateDTO.of(WS_DATE_TO_FROM, TITLE, StardateYearSource.EPISODE))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(YEAR_STRING_TO_FROM) >> Lists.newArrayList(
				new PageLink(
						title: YEAR_BARE_STRING_TO
				),
				new PageLink(
						title: YEAR_BARE_STRING_FROM
				)
		)
		stardateYearDTO.stardateFrom == STARDATE_FROM
		stardateYearDTO.stardateTo == STARDATE_TO
		stardateYearDTO.yearFrom == YEAR_INTEGER_FROM
		stardateYearDTO.yearTo == YEAR_INTEGER_TO
	}

	void "tolerates invalid dates"() {
		when:
		StardateYearDTO stardateYearDTO1 = stardateYearProcessor
				.process(StardateYearCandidateDTO.of(WS_DATE_INVALID_1, TITLE, StardateYearSource.EPISODE))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(INVALID) >> Lists.newArrayList()
		stardateYearDTO1.stardateFrom == STARDATE_FROM
		stardateYearDTO1.stardateTo == STARDATE_FROM
		stardateYearDTO1.yearFrom == null
		stardateYearDTO1.yearTo == null

		when:
		StardateYearDTO stardateYearDTO2 = stardateYearProcessor
				.process(StardateYearCandidateDTO.of(WS_DATE_INVALID_2, TITLE, StardateYearSource.EPISODE))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(YEAR_STRING_FROM) >> Lists.newArrayList(
				new PageLink(
						title: YEAR_BARE_STRING_FROM
				)
		)
		stardateYearDTO2.stardateFrom == null
		stardateYearDTO2.stardateTo == null
		stardateYearDTO2.yearFrom == YEAR_INTEGER_FROM
		stardateYearDTO2.yearTo == YEAR_INTEGER_FROM

		when:
		StardateYearDTO stardateYearDTO3 = stardateYearProcessor
				.process(StardateYearCandidateDTO.of(WS_DATE_INVALID_3, TITLE, StardateYearSource.EPISODE))

		then:
		0 * _
		stardateYearDTO3.stardateFrom == null
		stardateYearDTO3.stardateTo == null
		stardateYearDTO3.yearFrom == null
		stardateYearDTO3.yearTo == null

		when:
		StardateYearDTO stardateYearDTO4 = stardateYearProcessor
				.process(StardateYearCandidateDTO.of(WS_DATE_INVALID_4, TITLE, StardateYearSource.EPISODE))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(YEAR_TOO_EARLY_STRING) >> Lists.newArrayList(
				new PageLink(
						title: YEAR_TOO_EARLY_BARE_STRING
				)
		)
		0 * _
		stardateYearDTO4.stardateFrom == STARDATE_FROM
		stardateYearDTO4.stardateTo == STARDATE_FROM
		stardateYearDTO4.yearFrom == null
		stardateYearDTO4.yearTo == null

		when:
		StardateYearDTO stardateYearDTO5 = stardateYearProcessor
				.process(StardateYearCandidateDTO.of(WS_DATE_INVALID_5, TITLE, StardateYearSource.EPISODE))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(YEAR_TOO_LATE_STRING) >> Lists.newArrayList(
				new PageLink(
						title: YEAR_TOO_LATE_BARE_STRING
				)
		)
		0 * _
		stardateYearDTO5.stardateFrom == STARDATE_FROM
		stardateYearDTO5.stardateTo == STARDATE_FROM
		stardateYearDTO5.yearFrom == null
		stardateYearDTO5.yearTo == null

		when:
		StardateYearDTO stardateYearDTO6 = stardateYearProcessor
				.process(StardateYearCandidateDTO.of(WS_DATE_INVALID_6, TITLE, StardateYearSource.EPISODE))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(YEAR_INVALID_STRING) >> Lists.newArrayList(
				new PageLink(
						title: INVALID
				)
		)
		0 * _
		stardateYearDTO6.stardateFrom == STARDATE_FROM
		stardateYearDTO6.stardateTo == STARDATE_FROM
		stardateYearDTO6.yearFrom == null
		stardateYearDTO6.yearTo == null

		when:
		StardateYearDTO stardateYearDTO7 = stardateYearProcessor
				.process(StardateYearCandidateDTO.of(WS_DATE_INVALID_7, TITLE, StardateYearSource.EPISODE))

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(INVALID) >> Lists.newArrayList()
		0 * _
		stardateYearDTO7.stardateFrom == STARDATE_FROM
		stardateYearDTO7.stardateTo == STARDATE_FROM
		stardateYearDTO7.yearFrom == null
		stardateYearDTO7.yearTo == null

		when:
		StardateYearDTO stardateYearDTO8 = stardateYearProcessor.process(StardateYearCandidateDTO.of(null, TITLE, StardateYearSource.EPISODE))

		then:
		0 * _
		stardateYearDTO8.stardateFrom == null
		stardateYearDTO8.stardateTo == null
		stardateYearDTO8.yearFrom == null
		stardateYearDTO8.yearTo == null
	}

}
