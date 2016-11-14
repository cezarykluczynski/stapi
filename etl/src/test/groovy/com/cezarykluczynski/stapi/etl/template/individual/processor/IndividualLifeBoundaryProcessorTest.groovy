package com.cezarykluczynski.stapi.etl.template.individual.processor

import com.cezarykluczynski.stapi.etl.template.common.processor.PageLinkToYearProcessor
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualLifeBoundaryDTO
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.google.common.collect.Lists
import org.apache.commons.lang3.StringUtils
import spock.lang.Specification

class IndividualLifeBoundaryProcessorTest extends Specification {

	private static final Integer YEAR_1 = 1960
	private static final Integer YEAR_2 = 1970

	private PageLinkToYearProcessor pageLinkToYearProcessorMock

	private WikitextApi wikitextApiMock

	private IndividualLifeBoundaryProcessor individualLifeBoundaryProcessor

	def setup() {
		pageLinkToYearProcessorMock = Mock(PageLinkToYearProcessor)
		wikitextApiMock = Mock(WikitextApi)
		individualLifeBoundaryProcessor = new IndividualLifeBoundaryProcessor(wikitextApiMock,
				pageLinkToYearProcessorMock)
	}

	def "returns empty dto when wikitext has no links"() {
		when:
		IndividualLifeBoundaryDTO individualLifeBoundaryDTO = individualLifeBoundaryProcessor.process(StringUtils.EMPTY)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(StringUtils.EMPTY) >> Lists.newArrayList()
		ReflectionTestUtils.getNumberOfNotNullFields(individualLifeBoundaryDTO) == 0
	}

	def "sets only first value returned by PageLinkToYearProcessor"() {
		given:
		PageLink pageLink1 = Mock(PageLink)
		PageLink pageLink2 = Mock(PageLink)

		when:
		IndividualLifeBoundaryDTO individualLifeBoundaryDTO = individualLifeBoundaryProcessor.process(StringUtils.EMPTY)

		then:
		1 * wikitextApiMock.getPageLinksFromWikitext(StringUtils.EMPTY) >> Lists.newArrayList(pageLink1, pageLink2)
		1 * pageLinkToYearProcessorMock.process(pageLink1) >> YEAR_1
		1 * pageLinkToYearProcessorMock.process(pageLink2) >> YEAR_2
		individualLifeBoundaryDTO.year == YEAR_1
		ReflectionTestUtils.getNumberOfNotNullFields(individualLifeBoundaryDTO) == 1
	}

}
