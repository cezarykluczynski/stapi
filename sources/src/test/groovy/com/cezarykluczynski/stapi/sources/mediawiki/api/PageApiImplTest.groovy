package com.cezarykluczynski.stapi.sources.mediawiki.api

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.cache.PageCacheStorage
import com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki.BlikiConnector
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.service.complement.ParseComplementingService
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import com.google.common.collect.Lists
import info.bliki.api.PageInfo
import spock.lang.Specification

class PageApiImplTest extends Specification {

	private static final String TITLE_1 = 'Patrick Stewart'
	private static final String TITLE_2 = 'Brent Spiner'
	private static final String TITLE_3 = 'Jonathan Frakes'
	private static final String TITLE_4 = 'Shoud never be used'
	private static final String NS = 'NS'
	private static final String TITLE_NOT_FOUND = ''
	private static final String NOT_FOUND_TITLE = 'Harrison Ford'

	private static final Long PAGE_ID_1 = 2501L
	private static final String PAGE_ID_1_STRING = '2501'
	private static final Long PAGE_ID_2 = 1580L
	private static final Long PAGE_ID_3 = 1576L

	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private static final String XML_1 = createXml(TITLE_1, PAGE_ID_1)
	private static final String XML_2 = createXml(TITLE_2, PAGE_ID_2)
	private static final String XML_WITHOUT_WIKITEXT = createXml(TITLE_1, PAGE_ID_1, false)
	private static final String NOT_FOUND_XML = '''
		<api>
			<error code="missingtitle" info="The page you specified doesn&#039;t exist" xml:space="preserve"></error>
		</api>
'''
	private static final String INVALID_XML = '<'
	private static final String XML_REDIRECT_1 = createRedirectXml(TITLE_1, PAGE_ID_1, TITLE_2)
	private static final String XML_REDIRECT_2 = createRedirectXml(TITLE_2, PAGE_ID_2, TITLE_3)
	private static final String XML_REDIRECT_3 = createRedirectXml(TITLE_3, PAGE_ID_3, TITLE_4)
	private static final String XML_QUERY = """
<api>
	<query>
		<pages>
			<page pageid="${PAGE_ID_1_STRING}" ns="${NS}" title="${TITLE_1}" />
		</pages>
	</query>
</api>
"""

	private BlikiConnector blikiConnectorMock

	private WikitextApi wikitextApiMock

	private ParseComplementingService parseComplementingServiceMock

	PageCacheStorage pageCacheStorageMock

	private PageApiImpl pageApiImpl

	void setup() {
		blikiConnectorMock = Mock()
		wikitextApiMock = Mock()
		parseComplementingServiceMock = Mock()
		pageCacheStorageMock = Mock()
		pageApiImpl = new PageApiImpl(blikiConnectorMock, wikitextApiMock, parseComplementingServiceMock, pageCacheStorageMock)
	}

	void "gets page from title"() {
		when:
		Page page = pageApiImpl.getPage(TITLE_1, MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.getPage(TITLE_1, MEDIA_WIKI_SOURCE) >> XML_1
		page.pageId == PAGE_ID_1
		page.title == TITLE_1
		page.mediaWikiSource == MEDIA_WIKI_SOURCE

		then: 'sections are parsed'
		page.sections[0].anchor == 'Biography'
		page.sections[0].byteOffset == 11
		page.sections[0].number == '1'
		page.sections[0].level == 2
		page.sections[0].text == 'Biography'
		page.sections[0].wikitext == 'Biography content.'
		page.sections[1].anchor == 'Star_Trek'
		page.sections[1].byteOffset == 46
		page.sections[1].number == '2'
		page.sections[1].level == 2
		page.sections[1].text == '<i>Star Trek</i>'
		page.sections[1].wikitext == 'Star Trek content.'
	}

	void "gets page from PageCacheStorage when it was found there"() {
		given:
		Page page = Mock()

		when:
		Page pageOutput = pageApiImpl.getPage(TITLE_1, MEDIA_WIKI_SOURCE)

		then:
		1 * pageCacheStorageMock.get(TITLE_1, MEDIA_WIKI_SOURCE) >> page
		0 * _
		pageOutput == page
	}

	void "returns page when there is no wikitext"() {
		when:
		Page page = pageApiImpl.getPage(TITLE_1, MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.getPage(TITLE_1, MEDIA_WIKI_SOURCE) >> XML_WITHOUT_WIKITEXT
		1 * parseComplementingServiceMock.complement(_)
		page.pageId == PAGE_ID_1
		page.title == TITLE_1
		page.mediaWikiSource == MEDIA_WIKI_SOURCE
	}

	void "follows redirect"() {
		when:
		Page page = pageApiImpl.getPage(TITLE_1, MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.getPage(TITLE_1, MEDIA_WIKI_SOURCE) >> XML_REDIRECT_1
		1 * wikitextApiMock.getPageTitlesFromWikitext(_) >> Lists.newArrayList(TITLE_2)
		1 * blikiConnectorMock.getPage(TITLE_2, MEDIA_WIKI_SOURCE) >> XML_2
		2 * parseComplementingServiceMock.complement(_)
		page.pageId == PAGE_ID_2
		page.title == TITLE_2
		page.mediaWikiSource == MEDIA_WIKI_SOURCE
		page.redirectPath.size() == 1
		page.redirectPath[0].pageId == PAGE_ID_1
		page.redirectPath[0].title == TITLE_1
		page.redirectPath[0].mediaWikiSource == MEDIA_WIKI_SOURCE
	}

	void "returns null when redirect follows to unexisting page"() {
		when:
		Page page = pageApiImpl.getPage(TITLE_1, MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.getPage(TITLE_1, MEDIA_WIKI_SOURCE) >> XML_REDIRECT_1
		1 * wikitextApiMock.getPageTitlesFromWikitext(_) >> Lists.newArrayList(TITLE_2)
		1 * blikiConnectorMock.getPage(TITLE_2, MEDIA_WIKI_SOURCE) >> NOT_FOUND_XML
		1 * parseComplementingServiceMock.complement(_)
		page == null
	}

	void "does not follow more than 2 redirects"() {
		when:
		Page page = pageApiImpl.getPage(TITLE_1, MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.getPage(TITLE_1, MEDIA_WIKI_SOURCE) >> XML_REDIRECT_1
		1 * wikitextApiMock.getPageTitlesFromWikitext(_) >> Lists.newArrayList(TITLE_2)
		3 * parseComplementingServiceMock.complement(_)
		1 * blikiConnectorMock.getPage(TITLE_2, MEDIA_WIKI_SOURCE) >> XML_REDIRECT_2
		1 * wikitextApiMock.getPageTitlesFromWikitext(_) >> Lists.newArrayList(TITLE_3)
		1 * blikiConnectorMock.getPage(TITLE_3, MEDIA_WIKI_SOURCE) >> XML_REDIRECT_3
		0 * wikitextApiMock.getPageTitlesFromWikitext(_)
		page.pageId == PAGE_ID_3
		page.title == TITLE_3
		page.mediaWikiSource == MEDIA_WIKI_SOURCE
		page.redirectPath.size() == 2
		page.redirectPath[0].pageId == PAGE_ID_2
		page.redirectPath[0].title == TITLE_2
		page.redirectPath[0].mediaWikiSource == MEDIA_WIKI_SOURCE
		page.redirectPath[1].pageId == PAGE_ID_1
		page.redirectPath[1].title == TITLE_1
		page.redirectPath[1].mediaWikiSource == MEDIA_WIKI_SOURCE
	}

	void "returns page when redirect list is empty"() {
		when:
		Page page = pageApiImpl.getPage(TITLE_1, MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.getPage(TITLE_1, MEDIA_WIKI_SOURCE) >> XML_REDIRECT_1
		1 * parseComplementingServiceMock.complement(_)
		1 * wikitextApiMock.getPageTitlesFromWikitext(_) >> Lists.newArrayList()
		page.pageId == PAGE_ID_1
		page.title == TITLE_1
		page.mediaWikiSource == MEDIA_WIKI_SOURCE
	}

	void "returns null when page is not found"() {
		when: 'not found page is called'
		Page page = pageApiImpl.getPage(TITLE_NOT_FOUND, MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.getPage(TITLE_NOT_FOUND, MEDIA_WIKI_SOURCE) >> null
		0 * parseComplementingServiceMock.complement(_)
		page == null
	}

	void "converts exception thrown during parsing to StapiRuntimeException"() {
		when: 'not found page is called'
		pageApiImpl.getPage(TITLE_NOT_FOUND, MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.getPage(TITLE_NOT_FOUND, MEDIA_WIKI_SOURCE) >> INVALID_XML
		0 * parseComplementingServiceMock.complement(_)
		thrown(StapiRuntimeException)
	}

	void "gets pages from found titles"() {
		when:
		List<Page> pageList = pageApiImpl.getPages(Lists.newArrayList(TITLE_1, NOT_FOUND_TITLE, TITLE_2), MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.getPage(TITLE_1, MEDIA_WIKI_SOURCE) >> XML_1
		2 * parseComplementingServiceMock.complement(_)
		1 * blikiConnectorMock.getPage(NOT_FOUND_TITLE, MEDIA_WIKI_SOURCE) >> NOT_FOUND_XML
		1 * blikiConnectorMock.getPage(TITLE_2, MEDIA_WIKI_SOURCE) >> XML_2
		pageList.size() == 2
		pageList[0].title == TITLE_1
		pageList[0].mediaWikiSource == MEDIA_WIKI_SOURCE
		pageList[1].title == TITLE_2
		pageList[1].mediaWikiSource == MEDIA_WIKI_SOURCE
	}

	void "gets page info"() {
		when:
		PageInfo pageInfo = pageApiImpl.getPageInfo(TITLE_1, MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.getPageInfo(TITLE_1, MEDIA_WIKI_SOURCE) >> XML_QUERY
		pageInfo.title == TITLE_1
		pageInfo.pageid == PAGE_ID_1_STRING
		pageInfo.ns == NS
	}

	private static String createXml(String title, Long pageId, withWikitext = true) {
		String wikitext = withWikitext ? '''<wikitext xml:space="preserve">
Ten chars.
== Biography ==
Biography content.
== Star Trek ==
Star Trek content.
				</wikitext>''' : ''
		String sections = withWikitext ? '''<sections>
				<s toclevel="1" level="2" line="Biography" number="1" index="1" fromtitle="Patrick_Stewart"
						byteoffset="11" anchor="Biography"/>
				<s toclevel="1" level="2" line="&lt;i&gt;Star Trek&lt;/i&gt;" number="2" index="2" fromtitle="Patrick_Stewart"
						byteoffset="46" anchor="Star_Trek"/>
		</sections>''' : ''
		"""
			<api>
				<parse title=\"${title}\" pageid=\"${pageId}\">
					<parsetree xml:space="preserve">&lt;root&gt;&lt;/root&gt;</parsetree>
					${wikitext}
					${sections}
				</parse>
			</api>
		"""
	}

	private static String createRedirectXml(String title, Long pageId, String redirect) {
		"""
			<api>
				<parse title=\"${title}\" pageid=\"${pageId}\">
					<parsetree xml:space="preserve">&lt;root&gt;&lt;/root&gt;</parsetree>
					<wikitext xml:space="preserve"> #redirect [[${redirect}|Other page]] </wikitext>
				</parse>
			</api>
		"""
	}

}
