package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.template.common.processor.PageLinkToYearProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.DayMonthPageLinkProcessor
import com.cezarykluczynski.stapi.etl.template.individual.dto.DayMonthDTO
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualLifeBoundaryDTO
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.google.common.collect.Lists
import org.apache.commons.lang3.StringUtils
import spock.lang.Specification

import java.time.Month

class IndividualLifeBoundaryProcessorTest extends Specification {

	private static final Integer YEAR_1 = 1960
	private static final Integer YEAR_2 = 1970

	private PageLinkToYearProcessor pageLinkToYearProcessorMock

	private WikitextApi wikitextApiMock

	private DayMonthPageLinkProcessor dayMonthPageLinkProcessorMock

	private DayInMonthProximityFindingProcessor dayInMonthProximityFindingProcessorMock

	private IndividualLifeBoundaryProcessor individualLifeBoundaryProcessor

	void setup() {
		pageLinkToYearProcessorMock = Mock()
		wikitextApiMock = Mock()
		dayMonthPageLinkProcessorMock = Mock()
		dayInMonthProximityFindingProcessorMock = Mock()
		individualLifeBoundaryProcessor = new IndividualLifeBoundaryProcessor(wikitextApiMock, pageLinkToYearProcessorMock,
				dayMonthPageLinkProcessorMock, dayInMonthProximityFindingProcessorMock)
	}

	void "returns empty dto when wikitext has no links"() {
		when:
		IndividualLifeBoundaryDTO individualLifeBoundaryDTO = individualLifeBoundaryProcessor.process(StringUtils.EMPTY)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(StringUtils.EMPTY) >> Lists.newArrayList()
		0 * _
		ReflectionTestUtils.getNumberOfNotNullFields(individualLifeBoundaryDTO) == 0
	}

	void "sets only first value returned by PageLinkToYearProcessor"() {
		given:
		PageLink pageLink1 = Mock()
		PageLink pageLink2 = Mock()

		when:
		IndividualLifeBoundaryDTO individualLifeBoundaryDTO = individualLifeBoundaryProcessor.process(StringUtils.EMPTY)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(StringUtils.EMPTY) >> Lists.newArrayList(pageLink1, pageLink2)
		1 * pageLinkToYearProcessorMock.process(pageLink1) >> YEAR_1
		1 * pageLinkToYearProcessorMock.process(pageLink2) >> YEAR_2
		0 * _
		individualLifeBoundaryDTO.year == YEAR_1
		ReflectionTestUtils.getNumberOfNotNullFields(individualLifeBoundaryDTO) == 1
	}

	void "when no year was found, link is passed to DayMonthPageLinkProcessor"() {
		given:
		PageLink pageLink = Mock()
		Integer day = 2
		Month month = Month.SEPTEMBER

		when:
		IndividualLifeBoundaryDTO individualLifeBoundaryDTO = individualLifeBoundaryProcessor.process(StringUtils.EMPTY)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(StringUtils.EMPTY) >> Lists.newArrayList(pageLink)
		1 * pageLinkToYearProcessorMock.process(pageLink) >> null
		1 * dayMonthPageLinkProcessorMock.process(pageLink) >> DayMonthDTO.of(day, month)
		individualLifeBoundaryDTO.day == day
		individualLifeBoundaryDTO.month == month.value
		individualLifeBoundaryDTO.year == null
		ReflectionTestUtils.getNumberOfNotNullFields(individualLifeBoundaryDTO) == 2
	}

	void "when no year was found, and DayMonthPageLinkProcessor returns only month, day is looked up with DayInMonthProximityFindingProcessor"() {
		given:
		PageLink pageLink = Mock()
		Integer day = 2
		Month month = Month.SEPTEMBER

		when:
		IndividualLifeBoundaryDTO individualLifeBoundaryDTO = individualLifeBoundaryProcessor.process(StringUtils.EMPTY)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(StringUtils.EMPTY) >> Lists.newArrayList(pageLink)
		1 * pageLinkToYearProcessorMock.process(pageLink) >> null
		1 * dayMonthPageLinkProcessorMock.process(pageLink) >> DayMonthDTO.of(null, month)
		1 * dayInMonthProximityFindingProcessorMock.process(_) >> day
		individualLifeBoundaryDTO.day == day
		individualLifeBoundaryDTO.month == month.value
		individualLifeBoundaryDTO.year == null
		ReflectionTestUtils.getNumberOfNotNullFields(individualLifeBoundaryDTO) == 2
	}

}
