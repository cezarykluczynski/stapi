package com.cezarykluczynski.stapi.sources.mediawiki.parser

import info.bliki.api.PageInfo
import spock.lang.Specification

class XMLQueryParserTest extends Specification {

	private static final String TITLE = 'Patrick Stewart'
	private static final Long PAGE_ID = 2501
	private static final String PAGE_ID_STRING = '2501'
	private static final String NS = 'NS'

	private static final String XML = """
		<api>
			<query>
				<pages>
					<page pageid="${PAGE_ID}" ns="${NS}" title="${TITLE}" />
				</pages>
			</query>
		</api>
"""

	void "converts XML to Page"() {
		given:
		PageInfo expectedPageInfo = new PageInfo(
				pageid: PAGE_ID_STRING,
				ns: NS,
				title: TITLE
		)

		when:
		PageInfo pageInfo = new XMLQueryParser(XML).pageInfo

		then:
		pageInfo == expectedPageInfo
	}

}
