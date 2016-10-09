package com.cezarykluczynski.stapi.wiki.api

import com.cezarykluczynski.stapi.wiki.connector.bliki.BlikiConnector
import com.cezarykluczynski.stapi.wiki.dto.Page
import spock.lang.Specification

class PageApiImplTest extends Specification {

	private static final String TITLE = 'Patrick Stewart'
	private static final Long PAGE_ID = 2501L
	private static final String XML = """
		<api>
			<parse title=\"${TITLE}\" pageid=\"${PAGE_ID}\">
				<parsetree xml:space="preserve">&lt;root&gt;&lt;/root&gt;</parsetree>
			</parse>
		</api>
"""

	private BlikiConnector blikiConnectorMock

	private PageApiImpl pageApiImpl

	def setup() {
		blikiConnectorMock = Mock(BlikiConnector)
		pageApiImpl = new PageApiImpl(blikiConnectorMock)
	}

	def "gets page from title"() {
		when:
		Page page = pageApiImpl.getPage(TITLE)

		then:
		page.pageId == PAGE_ID
		page.title == TITLE
		1 * blikiConnectorMock.getPage(TITLE) >> XML
	}

	def "converts exceptions to runtime exceptions"() {
		when: "not found page is called"
		pageApiImpl.getPage("")

		then:
		thrown(RuntimeException)

	}

}
