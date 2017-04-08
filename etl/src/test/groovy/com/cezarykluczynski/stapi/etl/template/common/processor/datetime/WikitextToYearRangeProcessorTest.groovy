package com.cezarykluczynski.stapi.etl.template.common.processor.datetime

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.YearRange
import com.cezarykluczynski.stapi.etl.template.common.processor.PageLinkToYearProcessor
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template
import com.google.common.collect.Lists
import spock.lang.Specification

class WikitextToYearRangeProcessorTest extends Specification {

	private static final String WIKITEXT = 'WIKITEXT'
	private static final Integer YEAR_FROM = 2350
	private static final Integer YEAR_TO = 2390

	private PartToYearRangeProcessor partToYearRangeProcessorMock

	private WikitextApi wikitextApiMock

	private PageLinkToYearProcessor pageLinkToYearProcessorMock

	private WikitextToYearRangeProcessor wikitextToYearRangeProcessor

	void setup() {
		partToYearRangeProcessorMock = Mock()
		wikitextApiMock = Mock()
		pageLinkToYearProcessorMock = Mock()
		wikitextToYearRangeProcessor = new WikitextToYearRangeProcessor(partToYearRangeProcessorMock, wikitextApiMock, pageLinkToYearProcessorMock)
	}

	void "when PartToYearRangeProcessor returns non empty YearRange, it is returned"() {
		given:
		YearRange yearRangeWithYearFrom = new YearRange(YEAR_FROM, null)
		YearRange yearRangeWithYearTo = new YearRange(null, YEAR_TO)

		when:
		YearRange yearRangeWithYearFromOutput = wikitextToYearRangeProcessor.process(WIKITEXT)

		then:
		1 * partToYearRangeProcessorMock.process(_ as Template.Part) >> { Template.Part templatePart ->
			assert templatePart.value == WIKITEXT
			yearRangeWithYearFrom
		}
		0 * _
		yearRangeWithYearFromOutput == yearRangeWithYearFrom

		when:
		YearRange yearRangeWithYearToOutput = wikitextToYearRangeProcessor.process(WIKITEXT)

		then:
		1 * partToYearRangeProcessorMock.process(_ as Template.Part) >> { Template.Part templatePart ->
			assert templatePart.value == WIKITEXT
			yearRangeWithYearTo
		}
		0 * _
		yearRangeWithYearToOutput == yearRangeWithYearTo
	}

	void "when WikitextApi returns one link, it is used as a year from and year to"() {
		given:
		PageLink pageLink = Mock()

		when:
		YearRange yearRange = wikitextToYearRangeProcessor.process(WIKITEXT)

		then:
		1 * partToYearRangeProcessorMock.process(_ as Template.Part) >> null
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(pageLink)
		1 * pageLinkToYearProcessorMock.process(pageLink) >> YEAR_FROM
		0 * _
		yearRange.yearFrom == YEAR_FROM
		yearRange.yearTo == YEAR_FROM
	}

	void "when WikitextApi returns two links, one is used as a year from, and the second is used as a year to"() {
		given:
		PageLink pageLinkFrom = Mock()
		PageLink pageLinkTo = Mock()

		when:
		YearRange yearRange = wikitextToYearRangeProcessor.process(WIKITEXT)

		then:
		1 * partToYearRangeProcessorMock.process(_ as Template.Part) >> null
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList(pageLinkFrom, pageLinkTo)
		1 * pageLinkToYearProcessorMock.process(pageLinkFrom) >> YEAR_FROM
		1 * pageLinkToYearProcessorMock.process(pageLinkTo) >> YEAR_TO
		0 * _
		yearRange.yearFrom == YEAR_FROM
		yearRange.yearTo == YEAR_TO
	}

	void "returns null when dependencies could not find and years"() {
		when:
		YearRange yearRange = wikitextToYearRangeProcessor.process(WIKITEXT)

		then:
		1 * partToYearRangeProcessorMock.process(_ as Template.Part) >> null
		1 * wikitextApiMock.getPageLinksFromWikitext(WIKITEXT) >> Lists.newArrayList()
		0 * _
		yearRange == null
	}

}
