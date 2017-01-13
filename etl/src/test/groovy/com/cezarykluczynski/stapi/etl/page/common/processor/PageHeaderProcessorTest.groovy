package com.cezarykluczynski.stapi.etl.page.common.processor

import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.google.common.collect.Lists
import info.bliki.api.PageInfo
import spock.lang.Specification

class PageHeaderProcessorTest extends Specification {

	private static final Long PAGE_ID = 1L
	private static final String PAGE_ID_STRING = "1"
	private static final String TITLE = 'TITLE'
	private static final String TITLE_AFTER_REDIRECT = 'TITLE_AFTER_REDIRECT'
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
		Page page = new Page(pageId: PAGE_ID)

		when:
		Page pageOutput = pageHeaderProcessor.process(pageHeader)

		then:
		1 * pageApiMock.getPage(TITLE, MEDIA_WIKI_SOURCE) >> page
		0 * _
		page == pageOutput
	}

	def "should get page, and supplement page id from page header when redirect list is empty"() {
		given:
		PageHeader pageHeader = PageHeader.builder()
				.title(TITLE)
				.mediaWikiSource(MEDIA_WIKI_SOURCE)
				.pageId(PAGE_ID)
				.build()
		Page page = new Page()

		when:
		Page pageOutput = pageHeaderProcessor.process(pageHeader)

		then:
		1 * pageApiMock.getPage(TITLE, MEDIA_WIKI_SOURCE) >> page
		0 * _
		page == pageOutput
		page.pageId == PAGE_ID
	}


	def "should get page, and supplement page from another API call, when redirect list is not empty"() {
		given:
		PageHeader pageHeader = PageHeader.builder()
				.title(TITLE)
				.mediaWikiSource(MEDIA_WIKI_SOURCE)
				.build()
		Page page = new Page(title: TITLE_AFTER_REDIRECT, redirectPath: Lists.newArrayList(Mock(PageHeader)))
		PageInfo pageInfo = new PageInfo(pageid: PAGE_ID_STRING)

		when:
		Page pageOutput = pageHeaderProcessor.process(pageHeader)

		then:
		1 * pageApiMock.getPage(TITLE, MEDIA_WIKI_SOURCE) >> page
		1 * pageApiMock.getPageInfo(TITLE_AFTER_REDIRECT, MEDIA_WIKI_SOURCE) >> pageInfo
		0 * _
		page == pageOutput
		page.pageId == PAGE_ID
	}

}
