package com.cezarykluczynski.stapi.sources.mediawiki.parser

import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import com.google.common.collect.Lists
import spock.lang.Specification

class XMLParseParserTest extends Specification {

	private static final String TITLE = 'Patrick Stewart'
	private static final Long PAGE_ID = 2501
	private static final String CATEGORY_1 = 'Performers'
	private static final String CATEGORY_2 = 'TNG_performers'

	private static final String XML = """
		<api>
			<parse title="${TITLE}" pageid="${PAGE_ID}">
				<categories>
					<cl sortkey="Stewart, Patrick" missing="" xml:space="preserve">${CATEGORY_1}</cl>
					<cl sortkey="Stewart, Patrick" missing="" xml:space="preserve">${CATEGORY_2}</cl>
				</categories>
				<parsetree xml:space="preserve">&lt;root&gt;&lt;/root&gt;</parsetree>
			</parse>
		</api>
"""
	private static final String XML_WITHOUT_PAGE_ID_AND_PARSETREE = """
		<api>
			<parse title="${TITLE}">
				<categories>
					<cl sortkey="Stewart, Patrick" missing="" xml:space="preserve">${CATEGORY_1}</cl>
					<cl sortkey="Stewart, Patrick" missing="" xml:space="preserve">${CATEGORY_2}</cl>
				</categories>
			</parse>
		</api>
"""

	void "converts XML to Page"() {
		given:
		Page expectedPage = new Page(
				pageId: PAGE_ID,
				title: TITLE,
				categories: Lists.newArrayList(
						new CategoryHeader(title: CATEGORY_1),
						new CategoryHeader(title: CATEGORY_2)
				)
		)

		when:
		Page page = new XMLParseParser(XML).page

		then:
		page == expectedPage
	}

	void "tolerate XML with missing page id and not parseetree"() {
		given:
		Page expectedPage = new Page(
				title: TITLE,
				categories: Lists.newArrayList(
						new CategoryHeader(title: CATEGORY_1),
						new CategoryHeader(title: CATEGORY_2)
				)
		)

		when:
		Page page = new XMLParseParser(XML_WITHOUT_PAGE_ID_AND_PARSETREE).page

		then:
		page == expectedPage
	}

	void "convert exceptions to runtime exceptions"() {
		when:
		new XMLParseParser('').page

		then:
		thrown(StapiRuntimeException)
	}

}
