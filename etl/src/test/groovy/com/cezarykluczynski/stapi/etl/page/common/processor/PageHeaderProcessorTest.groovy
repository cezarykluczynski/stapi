package com.cezarykluczynski.stapi.etl.page.common.processor

import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import spock.lang.Specification

class PageHeaderProcessorTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private PageApi pageApiMock

	private PageHeaderProcessor pageHeaderProcessor

	def setup() {
		pageApiMock = Mock(PageApi)
		pageHeaderProcessor = new PageHeaderProcessor(pageApiMock)
	}

	def "should get page using page header's title"() {
		given:
		PageHeader pageHeader = PageHeader.builder()
				.title(TITLE)
				.mediaWikiSource(MEDIA_WIKI_SOURCE)
				.build()
		Page page = new Page()

		when:
		Page pageOutput = pageHeaderProcessor.process(pageHeader)

		then:
		1 * pageApiMock.getPage(TITLE, MEDIA_WIKI_SOURCE) >> page
		page == pageOutput
	}

}
