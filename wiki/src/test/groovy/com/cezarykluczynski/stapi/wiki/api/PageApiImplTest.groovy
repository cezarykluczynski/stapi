package com.cezarykluczynski.stapi.wiki.api

import com.cezarykluczynski.stapi.wiki.connector.bliki.BlikiConnector
import com.cezarykluczynski.stapi.wiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class PageApiImplTest extends Specification {

	private static final String TITLE_1 = 'Patrick Stewart'
	private static final Long PAGE_ID_1 = 2501L
	private static final String XML_1 = """
		<api>
			<parse title=\"${TITLE_1}\" pageid=\"${PAGE_ID_1}\">
				<parsetree xml:space="preserve">&lt;root&gt;&lt;/root&gt;</parsetree>
			</parse>
		</api>
"""
	private static final String TITLE_2 = 'Brent Spiner'
	private static final Long PAGE_ID_2 = 1580L
	private static final String XML_2 = """
		<api>
			<parse title=\"${TITLE_2}\" pageid=\"${PAGE_ID_2}\">
				<parsetree xml:space="preserve">&lt;root&gt;&lt;/root&gt;</parsetree>
			</parse>
		</api>
"""
	private static final String NOT_FOUND_TITLE = 'Harrison Ford'
	private static final String NOT_FOUND_XML = '''
		<api>
			<error code="missingtitle" info="The page you specified doesn&#039;t exist" xml:space="preserve"></error>
		</api>
'''

	private BlikiConnector blikiConnectorMock

	private PageApiImpl pageApiImpl

	def setup() {
		blikiConnectorMock = Mock(BlikiConnector)
		pageApiImpl = new PageApiImpl(blikiConnectorMock)
	}

	def "gets page from title"() {
		when:
		Page page = pageApiImpl.getPage(TITLE_1)

		then:
		1 * blikiConnectorMock.getPage(TITLE_1) >> XML_1
		page.pageId == PAGE_ID_1
		page.title == TITLE_1
	}

	def "converts exceptions to runtime exceptions"() {
		when: "not found page is called"
		pageApiImpl.getPage("")

		then:
		thrown(RuntimeException)
	}

	def "gets pages from found titles"() {
		when:
		List<Page> pageList = pageApiImpl.getPages(Lists.newArrayList(TITLE_1, NOT_FOUND_TITLE, TITLE_2))

		then:
		1 * blikiConnectorMock.getPage(TITLE_1) >> XML_1
		1 * blikiConnectorMock.getPage(NOT_FOUND_TITLE) >> NOT_FOUND_XML
		1 * blikiConnectorMock.getPage(TITLE_2) >> XML_2
		pageList.size() == 2
		pageList[0].title == TITLE_1
		pageList[1].title == TITLE_2
	}

}
