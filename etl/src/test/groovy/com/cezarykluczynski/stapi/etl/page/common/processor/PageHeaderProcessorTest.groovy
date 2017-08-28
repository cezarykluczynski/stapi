package com.cezarykluczynski.stapi.etl.page.common.processor

import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import com.google.common.collect.Lists
import info.bliki.api.PageInfo
import spock.lang.Specification

class PageHeaderProcessorTest extends Specification {

	private static final Long PAGE_ID = 1L
	private static final Long REDIRECT_PAGE_ID = 2L
	private static final String PAGE_ID_STRING = '1'
	private static final String REDIRECT_PAGE_ID_STRING = '2'
	private static final String TITLE = 'TITLE'
	private static final String TITLE_AFTER_REDIRECT = 'TITLE_AFTER_REDIRECT'
	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private PageApi pageApiMock

	private PageHeaderProcessor pageHeaderProcessor

	void setup() {
		pageApiMock = Mock()
		pageHeaderProcessor = new PageHeaderProcessor(pageApiMock)
	}

	void "when Page Api returns null, null is returned"() {
		given:
		PageHeader pageHeader = new PageHeader()

		when:
		Page page = pageHeaderProcessor.process(pageHeader)

		then:
		1 * pageApiMock.getPage(*_) >> null
		page == null
	}

	void "gets page using page header's title"() {
		given:
		PageHeader pageHeader = new PageHeader(
				title: TITLE,
				mediaWikiSource: MEDIA_WIKI_SOURCE)
		Page page = new Page(pageId: PAGE_ID)

		when:
		Page pageOutput = pageHeaderProcessor.process(pageHeader)

		then:
		1 * pageApiMock.getPage(TITLE, MEDIA_WIKI_SOURCE) >> page
		0 * _
		pageOutput == page
	}

	void "gets page, and supplement page id from page header when redirect list is empty"() {
		given:
		PageHeader pageHeader = new PageHeader(
				title: TITLE,
				mediaWikiSource: MEDIA_WIKI_SOURCE,
				pageId: PAGE_ID)
		Page page = new Page()

		when:
		Page pageOutput = pageHeaderProcessor.process(pageHeader)

		then:
		1 * pageApiMock.getPage(TITLE, MEDIA_WIKI_SOURCE) >> page
		0 * _
		pageOutput == page
		pageOutput.pageId == PAGE_ID
	}

	void "gets page, and supplement page id from page header and redirect list item, when redirect list is not empty"() {
		given:
		PageHeader pageHeader = new PageHeader(
				title: TITLE,
				mediaWikiSource: MEDIA_WIKI_SOURCE,
				pageId: PAGE_ID)
		PageHeader redirectPageHeader =  new PageHeader(
				title: TITLE_AFTER_REDIRECT,
				mediaWikiSource: MEDIA_WIKI_SOURCE,
				pageId: REDIRECT_PAGE_ID)
		Page page = new Page(redirectPath: Lists.newArrayList(redirectPageHeader))

		when:
		Page pageOutput = pageHeaderProcessor.process(pageHeader)

		then:
		1 * pageApiMock.getPage(TITLE, MEDIA_WIKI_SOURCE) >> page
		0 * _
		pageOutput == page
		pageOutput.pageId == PAGE_ID
		pageOutput.redirectPath[0].pageId == REDIRECT_PAGE_ID
	}

	void "gets page, and supplement page if for both page header and redirect list items"() {
		given:
		PageHeader pageHeader = new PageHeader(
				title: TITLE,
				mediaWikiSource: MEDIA_WIKI_SOURCE)
		PageHeader redirectPageHeader =  new PageHeader(
				title: TITLE_AFTER_REDIRECT,
				mediaWikiSource: MEDIA_WIKI_SOURCE)
		Page page = new Page(
				title: TITLE_AFTER_REDIRECT,
				redirectPath: Lists.newArrayList(redirectPageHeader))
		PageInfo pageInfo = new PageInfo(pageid: PAGE_ID_STRING)
		PageInfo redirectPageInfo = new PageInfo(pageid: REDIRECT_PAGE_ID_STRING)

		when:
		Page pageOutput = pageHeaderProcessor.process(pageHeader)

		then:
		1 * pageApiMock.getPage(TITLE, MEDIA_WIKI_SOURCE) >> page
		1 * pageApiMock.getPageInfo(TITLE, MEDIA_WIKI_SOURCE) >> pageInfo
		1 * pageApiMock.getPageInfo(TITLE_AFTER_REDIRECT, MEDIA_WIKI_SOURCE) >> redirectPageInfo
		pageOutput == page
		pageOutput.pageId == PAGE_ID
		pageOutput.redirectPath[0].pageId == REDIRECT_PAGE_ID
	}

	void "throws exception when PageInfo for original page is null"() {
		given:
		PageHeader pageHeader = new PageHeader(
				title: TITLE,
				mediaWikiSource: MEDIA_WIKI_SOURCE)
		PageHeader pageHeaderRedirect = Mock()
		Page page = new Page(
				title: TITLE_AFTER_REDIRECT,
				redirectPath: Lists.newArrayList(pageHeaderRedirect))

		when:
		pageHeaderProcessor.process(pageHeader)

		then:
		1 * pageApiMock.getPage(TITLE, MEDIA_WIKI_SOURCE) >> page
		1 * pageApiMock.getPageInfo(TITLE, MEDIA_WIKI_SOURCE) >> null
		thrown(StapiRuntimeException)
	}

	void "throws exception when PageInfo for item from redirect list is null"() {
		given:
		PageHeader pageHeader = new PageHeader(
				title: TITLE,
				mediaWikiSource: MEDIA_WIKI_SOURCE)
		PageHeader redirectPageHeader =  new PageHeader(
				title: TITLE_AFTER_REDIRECT,
				mediaWikiSource: MEDIA_WIKI_SOURCE)
		Page page = new Page(
				title: TITLE,
				redirectPath: Lists.newArrayList(redirectPageHeader))
		PageInfo pageInfo = new PageInfo(pageid: PAGE_ID_STRING)

		when:
		pageHeaderProcessor.process(pageHeader)

		then:
		1 * pageApiMock.getPage(TITLE, MEDIA_WIKI_SOURCE) >> page
		1 * pageApiMock.getPageInfo(TITLE, MEDIA_WIKI_SOURCE) >> pageInfo
		1 * pageApiMock.getPageInfo(TITLE_AFTER_REDIRECT, MEDIA_WIKI_SOURCE) >> null
		thrown(StapiRuntimeException)
	}

}
