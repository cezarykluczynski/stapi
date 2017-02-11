package com.cezarykluczynski.stapi.sources.mediawiki.converter

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.sources.mediawiki.util.constant.MemoryAlpha
import com.google.common.collect.Lists
import info.bliki.api.PageInfo
import spock.lang.Specification

class PageHeaderConverterTest extends Specification {

	private static final String TITLE_1 = 'TITLE_1'
	private static final String TITLE_2 = 'TITLE_2'
	private static final String PAGE_ID_1_STRING = '5'
	private static final String PAGE_ID_2_STRING = '10'
	private static final Long PAGE_ID_1_LONG = 5L
	private static final Long PAGE_ID_2_LONG = 10L
	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private PageHeaderConverter pageHeaderConverter

	void setup() {
		pageHeaderConverter = new PageHeaderConverter()
	}

	void "converts PageInfo list to PageHeader list, while filtering out pages from non-content namespaces"() {
		given:
		List<PageInfo> pageInfoList = Lists.newArrayList(
				new PageInfo(ns: MemoryAlpha.CONTENT_NAMESPACE, title: TITLE_1, pageid: PAGE_ID_1_STRING),
				new PageInfo(ns: '1'),
				new PageInfo(ns: MemoryAlpha.CONTENT_NAMESPACE, title: TITLE_2, pageid: PAGE_ID_2_STRING))

		when:
		List<PageHeader> pageHeaderList = pageHeaderConverter.fromPageInfoList(pageInfoList, MEDIA_WIKI_SOURCE)

		then:
		pageHeaderList.size() == 2
		pageHeaderList[0].pageId == PAGE_ID_1_LONG
		pageHeaderList[0].title == TITLE_1
		pageHeaderList[0].mediaWikiSource == MEDIA_WIKI_SOURCE
		pageHeaderList[1].pageId == PAGE_ID_2_LONG
		pageHeaderList[1].title == TITLE_2
		pageHeaderList[1].mediaWikiSource == MEDIA_WIKI_SOURCE
	}

	void "converts Page list to PageHeader list"() {
		given:
		List<Page> pageList = Lists.newArrayList(
				new Page(title: TITLE_1, pageId: PAGE_ID_1_LONG, mediaWikiSource: MEDIA_WIKI_SOURCE),
				new Page(title: TITLE_2, pageId: PAGE_ID_2_LONG, mediaWikiSource: MEDIA_WIKI_SOURCE))

		when:
		List<PageHeader> pageHeaderList = pageHeaderConverter.fromPageList(pageList)

		then:
		pageHeaderList.size() == 2
		pageHeaderList[0].pageId == PAGE_ID_1_LONG
		pageHeaderList[0].title == TITLE_1
		pageHeaderList[0].mediaWikiSource == MEDIA_WIKI_SOURCE
		pageHeaderList[1].pageId == PAGE_ID_2_LONG
		pageHeaderList[1].title == TITLE_2
		pageHeaderList[1].mediaWikiSource == MEDIA_WIKI_SOURCE
	}

}
