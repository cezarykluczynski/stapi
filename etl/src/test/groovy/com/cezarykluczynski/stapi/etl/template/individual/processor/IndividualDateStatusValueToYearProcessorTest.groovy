package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.template.common.processor.PageLinkToYearProcessor
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.google.common.collect.Lists
import org.apache.commons.lang3.StringUtils
import spock.lang.Specification

class IndividualDateStatusValueToYearProcessorTest extends Specification {

	private static final String YEAR_STRING = '1960'
	private static final String INVALID_YEAR_STRING = 'Not a year'
	private static final Integer YEAR_INTEGER = 1960
	private static final Integer YEAR_INTEGER_2 = 1970

	private WikitextApi wikitextApiMock

	private PageLinkToYearProcessor pageLinkToYearProcessorMock

	private IndividualDateStatusValueToYearProcessor individualDateStatusValueToYearProcessor

	void setup() {
		wikitextApiMock = Mock()
		pageLinkToYearProcessorMock = Mock()
		individualDateStatusValueToYearProcessor = new IndividualDateStatusValueToYearProcessor(wikitextApiMock,
				pageLinkToYearProcessorMock)
	}

	void "returns null for empty value"() {
		when:
		Integer year = individualDateStatusValueToYearProcessor.process(StringUtils.EMPTY)

		then:
		year == null
	}

	void "returns null when input contains 'century'"() {
		when:
		Integer year = individualDateStatusValueToYearProcessor.process(IndividualDateStatusValueToYearProcessor.CENTURY)

		then:
		year == null
	}

	void "returns null when input is a decade"() {
		when:
		Integer year = individualDateStatusValueToYearProcessor.process('2360s')

		then:
		year == null
	}

	void "returns null when input cannot be casted to integer"() {
		when:
		Integer year = individualDateStatusValueToYearProcessor.process(INVALID_YEAR_STRING)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(_) >> Lists.newArrayList()
		year == null
	}

	void "returns integer when input can be casted to integer"() {
		when:
		Integer year = individualDateStatusValueToYearProcessor.process(YEAR_STRING)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(_) >> Lists.newArrayList()
		year == YEAR_INTEGER
	}

	void "returns integer when one link to year is found"() {
		given:
		PageLink pageLink = Mock()

		when:
		Integer year = individualDateStatusValueToYearProcessor.process(YEAR_STRING)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(_) >> Lists.newArrayList(pageLink)
		1 * pageLinkToYearProcessorMock.process(pageLink) >> YEAR_INTEGER
		year == YEAR_INTEGER
	}

	void "returns null when more than one link to year is found"() {
		given:
		PageLink pageLink = Mock()
		PageLink pageLink2 = Mock()

		when:
		Integer year = individualDateStatusValueToYearProcessor.process(INVALID_YEAR_STRING)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(_) >> Lists.newArrayList(pageLink, pageLink2)
		1 * pageLinkToYearProcessorMock.process(pageLink) >> YEAR_INTEGER
		1 * pageLinkToYearProcessorMock.process(pageLink2) >> YEAR_INTEGER_2
		year == null
	}

}
