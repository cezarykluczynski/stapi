package com.cezarykluczynski.stapi.sources.mediawiki.api

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki.BlikiConnector
import com.cezarykluczynski.stapi.sources.mediawiki.converter.PageHeaderConverter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.sources.mediawiki.util.constant.ApiParams
import com.google.common.collect.Lists
import info.bliki.api.PageInfo
import spock.lang.Specification

import java.util.stream.Collectors

class CategoryApiImplTest extends Specification {

	private static final String TITLE_1 = 'TITLE_1'
	private static final String TITLE_2 = 'TITLE_2'
	private static final String TITLE_3 = 'TITLE_3'
	private static final String CM_CONTINUE = "abc"
	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN
	private static final String VALID_XML = """<?xml version="1.0"?>
		<api batchcomplete="">
			<query>
				<categorymembers>
				</categorymembers>
			</query>
		</api>
	"""
	private static final String VALID_XML_WITH_PAGES_AND_CATEGORIES = """<?xml version="1.0"?>
		<api batchcomplete="">
			<query>
				<categorymembers>
					<cm pageid="61470" ns="0" title="${TITLE_2}" />
					<cm pageid="60187" ns="14" title="Category:Composers" />
				</categorymembers>
			</query>
		</api>
	"""
	private static final String VALID_XML_WITH_PAGES_AND_PARENT_CATEGORY = """<?xml version="1.0"?>
		<api batchcomplete="">
			<query>
				<categorymembers>
					<cm pageid="61470" ns="0" title="${TITLE_2}" />
					<cm pageid="60187" ns="14" title="Category:${TITLE_1}" />
					<cm pageid="169866" ns="0" title="${TITLE_3}" />
				</categorymembers>
			</query>
		</api>
	"""
	private static final String VALID_XML_CONTINUE = """<?xml version="1.0"?>
		<api batchcomplete="">
			<continue cmcontinue="${CM_CONTINUE}"></continue>
			<query>
				<categorymembers>
				</categorymembers>
			</query>
		</api>
	"""
	private static final String INVALID_XML = 'INVALID_XML'

	private BlikiConnector blikiConnectorMock

	private PageHeaderConverter pageHeaderConverterMock

	private CategoryApiImpl categoryApiImpl

	def setup() {
		blikiConnectorMock = Mock(BlikiConnector)
		pageHeaderConverterMock = Mock(PageHeaderConverter)
		categoryApiImpl = new CategoryApiImpl(blikiConnectorMock, pageHeaderConverterMock)
	}

	def "gets pages in category by category title"() {
		given:
		List<PageHeader> pageHeaderListInput = Lists.newArrayList()


		when:
		List<PageHeader> pageHeaderListOutput = categoryApiImpl.getPages(TITLE_1, MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.readXML(_ as Map) >> { Map map ->
			assert map.get(ApiParams.KEY_LIST) == ApiParams.KEY_LIST_VALUE_CATEGORYMEMBERS
			assert map.get(ApiParams.KEY_CATEGORY_TITLE) == ApiParams.KEY_CATEGORY_TITLE_VALUE_PREFIX + TITLE_1
			assert map.get(ApiParams.KEY_CATEGORY_LIMIT) == ApiParams.KEY_CATEGORY_LIMIT_VALUE
			return VALID_XML
		}
		1 * pageHeaderConverterMock.fromPageInfoList(_, MEDIA_WIKI_SOURCE) >> pageHeaderListInput
		pageHeaderListOutput == pageHeaderListInput
	}

	def "follows cmcontinue attribute if continue tag is present"() {
		given:
		List<PageHeader> pageHeaderListInput = Lists.newArrayList()

		when:
		List<PageHeader> pageHeaderListOutput = categoryApiImpl.getPages(TITLE_1, MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.readXML(_ as Map) >> { Map map ->
			assert map.size() == 3
			assert map.get(ApiParams.KEY_LIST) == ApiParams.KEY_LIST_VALUE_CATEGORYMEMBERS
			assert map.get(ApiParams.KEY_CATEGORY_TITLE) == ApiParams.KEY_CATEGORY_TITLE_VALUE_PREFIX + TITLE_1
			assert map.get(ApiParams.KEY_CATEGORY_LIMIT) == ApiParams.KEY_CATEGORY_LIMIT_VALUE
			return VALID_XML_CONTINUE
		}
		1 * blikiConnectorMock.readXML(_ as Map) >> { Map map ->
			assert map.size() == 4
			assert map.get(ApiParams.KEY_LIST) == ApiParams.KEY_LIST_VALUE_CATEGORYMEMBERS
			assert map.get(ApiParams.KEY_CATEGORY_TITLE) == ApiParams.KEY_CATEGORY_TITLE_VALUE_PREFIX + TITLE_1
			assert map.get(ApiParams.KEY_CATEGORY_LIMIT) == ApiParams.KEY_CATEGORY_LIMIT_VALUE
			assert map.get(ApiParams.KEY_CATEGORY_CONTINIUE) == CM_CONTINUE
			return VALID_XML
		}
		1 * pageHeaderConverterMock.fromPageInfoList(_, MEDIA_WIKI_SOURCE) >> pageHeaderListInput

		pageHeaderListOutput == pageHeaderListInput
	}

	def "throws runtime exception when pages in category cannot be retrieved by title"() {
		given: "invalid XML_1 is passed XMLCategoryMembersParser"
		blikiConnectorMock.readXML(_ as Map) >> INVALID_XML

		when:
		categoryApiImpl.getPages(TITLE_1, MEDIA_WIKI_SOURCE)

		then:
		thrown(RuntimeException)
	}

	def "gets pages in category, including subcategories"() {
		given:
		List<PageHeader> pageHeaderList = Lists.newArrayList()

		when:
		List<PageHeader> pageHeaderListOutput = categoryApiImpl.getPagesIncludingSubcategories(TITLE_1, MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.readXML(_ as Map) >> VALID_XML_WITH_PAGES_AND_CATEGORIES
		1 * blikiConnectorMock.readXML(_ as Map) >> VALID_XML_WITH_PAGES_AND_PARENT_CATEGORY
		1 * pageHeaderConverterMock.fromPageInfoList(_ as List<PageInfo>, MEDIA_WIKI_SOURCE) >> { List<PageInfo> pageInfoList, MediaWikiSource mediaWikiSource ->
			assert pageInfoList.size() == 2
			List<String> titles = pageInfoList.stream()
					.map({pageHeader -> pageHeader.getTitle()})
					.collect(Collectors.toList())
			assert titles.contains(TITLE_2)
			assert titles.contains(TITLE_3)
			return pageHeaderList
		}
		pageHeaderListOutput == pageHeaderList
	}

}
