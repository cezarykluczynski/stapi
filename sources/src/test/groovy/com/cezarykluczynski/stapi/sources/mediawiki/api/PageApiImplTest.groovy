package com.cezarykluczynski.stapi.sources.mediawiki.api

import com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki.BlikiConnector
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification

class PageApiImplTest extends Specification {

	private static final String TITLE_1 = 'Patrick Stewart'
	private static final String TITLE_2 = 'Brent Spiner'
	private static final String TITLE_3 = 'Jonathan Frakes'
	private static final String TITLE_4 = 'Shoud never be used'
	private static final String NOT_FOUND_TITLE = 'Harrison Ford'

	private static final Long PAGE_ID_1 = 2501L
	private static final Long PAGE_ID_2 = 1580L
	private static final Long PAGE_ID_3 = 1576L

	private static final String XML_1 = createXml(TITLE_1, PAGE_ID_1)
	private static final String XML_2 = createXml(TITLE_2, PAGE_ID_2)
	private static final String XML_WITHOUT_WIKITEXT = createXml(TITLE_1, PAGE_ID_1, false)
	private static final String NOT_FOUND_XML = '''
		<api>
			<error code="missingtitle" info="The page you specified doesn&#039;t exist" xml:space="preserve"></error>
		</api>
'''
	private static final String XML_REDIRECT_1 = createRedirectXml(TITLE_1, PAGE_ID_1, TITLE_2)
	private static final String XML_REDIRECT_2 = createRedirectXml(TITLE_2, PAGE_ID_2, TITLE_3)
	private static final String XML_REDIRECT_3 = createRedirectXml(TITLE_3, PAGE_ID_3, TITLE_4)

	private BlikiConnector blikiConnectorMock

	private WikitextApi wikitextApiMock

	private PageApiImpl pageApiImpl

	def setup() {
		blikiConnectorMock = Mock(BlikiConnector)
		wikitextApiMock = Mock(WikitextApi)
		pageApiImpl = new PageApiImpl(blikiConnectorMock, wikitextApiMock)
	}

	def "gets page from title"() {
		when:
		Page page = pageApiImpl.getPage(TITLE_1)

		then:
		1 * blikiConnectorMock.getPage(TITLE_1) >> XML_1
		page.pageId == PAGE_ID_1
		page.title == TITLE_1
	}

	def "returns page when there is no wikitext"() {
		when:
		Page page = pageApiImpl.getPage(TITLE_1)

		then:
		1 * blikiConnectorMock.getPage(TITLE_1) >> XML_WITHOUT_WIKITEXT
		page.pageId == PAGE_ID_1
		page.title == TITLE_1
	}

	def "follows redirect"() {
		when:
		Page page = pageApiImpl.getPage(TITLE_1)

		then:
		1 * blikiConnectorMock.getPage(TITLE_1) >> XML_REDIRECT_1
		1 * wikitextApiMock.getPageTitlesFromWikitext(_) >> Lists.newArrayList(TITLE_2)
		1 * blikiConnectorMock.getPage(TITLE_2) >> XML_2
		page.pageId == PAGE_ID_2
		page.title == TITLE_2
	}

	def "does not follow more than 2 redirects"() {
		when:
		Page page = pageApiImpl.getPage(TITLE_1)

		then:
		1 * blikiConnectorMock.getPage(TITLE_1) >> XML_REDIRECT_1
		1 * wikitextApiMock.getPageTitlesFromWikitext(_) >> Lists.newArrayList(TITLE_2)
		1 * blikiConnectorMock.getPage(TITLE_2) >> XML_REDIRECT_2
		1 * wikitextApiMock.getPageTitlesFromWikitext(_) >> Lists.newArrayList(TITLE_3)
		1 * blikiConnectorMock.getPage(TITLE_3) >> XML_REDIRECT_3
		0 * wikitextApiMock.getPageTitlesFromWikitext(_)
		page.pageId == PAGE_ID_3
		page.title == TITLE_3
	}

	def "returns page when redirect list is empty"() {
		when:
		Page page = pageApiImpl.getPage(TITLE_1)

		then:
		1 * blikiConnectorMock.getPage(TITLE_1) >> XML_REDIRECT_1
		1 * wikitextApiMock.getPageTitlesFromWikitext(_) >> Lists.newArrayList()
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

	private static String createXml(String title, Long pageId, withWikitext = true) {
		String wikitext = withWikitext ? '<wikitext xml:space="preserve"> Some wikitext </wikitext>' : ''
		return """
			<api>
				<parse title=\"${title}\" pageid=\"${pageId}\">
					<parsetree xml:space="preserve">&lt;root&gt;&lt;/root&gt;</parsetree>
					${wikitext}
				</parse>
			</api>
		"""
	}

	private static String createRedirectXml(String title, Long pageId, String redirect) {
		return """
			<api>
				<parse title=\"${title}\" pageid=\"${pageId}\">
					<parsetree xml:space="preserve">&lt;root&gt;&lt;/root&gt;</parsetree>
					<wikitext xml:space="preserve"> #redirect [[${redirect}|Other page]] </wikitext>
				</parse>
			</api>
		"""
	}

}
