package com.cezarykluczynski.stapi.etl.page.common.processor

import com.cezarykluczynski.stapi.wiki.api.PageApi
import com.cezarykluczynski.stapi.wiki.dto.Page
import com.cezarykluczynski.stapi.wiki.dto.PageHeader
import spock.lang.Specification

class PageHeaderProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'

	private PageApi pageApiMock

	private PageHeaderProcessor pageHeaderProcessor

	def setup() {
		pageApiMock = Mock(PageApi)
		pageHeaderProcessor = new PageHeaderProcessor(pageApiMock)
	}

	def "should get page using page header's title"() {
		given:
		PageHeader pageHeader = PageHeader.builder().title(TITLE).build()
		Page page = new Page()

		when:
		Page pageOutput = pageHeaderProcessor.process(pageHeader)

		then:
		1 * pageApiMock.getPage(TITLE) >> page
		page == pageOutput
	}

}
