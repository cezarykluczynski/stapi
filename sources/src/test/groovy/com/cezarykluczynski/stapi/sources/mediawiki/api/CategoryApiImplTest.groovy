package com.cezarykluczynski.stapi.sources.mediawiki.api

import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki.BlikiConnector
import com.cezarykluczynski.stapi.sources.mediawiki.converter.PageHeaderConverter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.sources.mediawiki.util.constant.ApiParams
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import com.google.common.collect.Lists
import info.bliki.api.PageInfo
import spock.lang.Specification

import java.util.regex.Pattern
import java.util.stream.Collectors

class CategoryApiImplTest extends Specification {

	private static final String TITLE_1 = 'TITLE_1'
	private static final String TITLE_2 = 'TITLE_2'
	private static final String TITLE_3 = 'TITLE_3'
	private static final String CM_CONTINUE = 'abc'
	private static final MediaWikiSource MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN
	private static final String VALID_XML = '''<?xml version="1.0"?>
		<api batchcomplete="">
			<query>
				<categorymembers>
				</categorymembers>
			</query>
		</api>
	'''
	private static final String VALID_XML_WITH_PAGES_AND_CATEGORIES_1 = """<?xml version="1.0"?>
		<api batchcomplete="">
			<query>
				<categorymembers>
					<cm pageid="61470" ns="0" title="${TITLE_1}" />
					<cm pageid="60187" ns="14" title="Category:Producers" />
				</categorymembers>
			</query>
		</api>
	"""
	private static final String VALID_XML_WITH_PAGES_AND_CATEGORIES_2 = """<?xml version="1.0"?>
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
	private static final String VALID_XML_WITH_PAGES_AND_CATEGORY_TO_EXCLUDE = """<?xml version="1.0"?>
		<api batchcomplete="">
			<query>
				<categorymembers>
					<cm pageid="61470" ns="0" title="${TITLE_2}" />
					<cm pageid="60187" ns="14" title="Category:${TITLE_3}" />
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

	void setup() {
		blikiConnectorMock = Mock()
		pageHeaderConverterMock = Mock()
		categoryApiImpl = new CategoryApiImpl(blikiConnectorMock, pageHeaderConverterMock)
	}

	void "gets pages in category by category title"() {
		given:
		PageHeader pageHeader = Mock()
		List<PageHeader> pageHeaderListInput = Lists.newArrayList(pageHeader)

		when:
		List<PageHeader> pageHeaderListOutput = categoryApiImpl.getPages(TITLE_1, MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.readXML(_ as Map, _ as MediaWikiSource) >> { Map map, MediaWikiSource mediaWikiSource ->
			assert map.get(ApiParams.KEY_LIST) == ApiParams.KEY_LIST_VALUE_CATEGORYMEMBERS
			assert map.get(ApiParams.KEY_CATEGORY_TITLE) == ApiParams.KEY_CATEGORY_TITLE_VALUE_PREFIX + TITLE_1
			assert map.get(ApiParams.KEY_CATEGORY_LIMIT) == ApiParams.KEY_CATEGORY_LIMIT_VALUE
			VALID_XML
		}
		1 * pageHeaderConverterMock.fromPageInfoList(_, MEDIA_WIKI_SOURCE) >> pageHeaderListInput
		0 * _
		pageHeaderListOutput.contains pageHeader
	}

	void "gets pages in mutliple categories by category title"() {
		given:
		PageHeader pageHeader1 = Mock()
		PageHeader pageHeader2 = Mock()
		List<PageHeader> pageHeaderListInput1 = Lists.newArrayList(pageHeader1)
		List<PageHeader> pageHeaderListInput2 = Lists.newArrayList(pageHeader2)

		when:
		List<PageHeader> pageHeaderListOutput = categoryApiImpl.getPages(Lists.newArrayList(TITLE_1, TITLE_2), MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.readXML(_ as Map, _ as MediaWikiSource) >> { Map map, MediaWikiSource mediaWikiSource ->
			assert map.get(ApiParams.KEY_LIST) == ApiParams.KEY_LIST_VALUE_CATEGORYMEMBERS
			assert map.get(ApiParams.KEY_CATEGORY_TITLE) == ApiParams.KEY_CATEGORY_TITLE_VALUE_PREFIX + TITLE_1
			assert map.get(ApiParams.KEY_CATEGORY_LIMIT) == ApiParams.KEY_CATEGORY_LIMIT_VALUE
			VALID_XML_WITH_PAGES_AND_CATEGORIES_1
		}
		1 * blikiConnectorMock.readXML(_ as Map, _ as MediaWikiSource) >> { Map map, MediaWikiSource mediaWikiSource ->
			assert map.get(ApiParams.KEY_LIST) == ApiParams.KEY_LIST_VALUE_CATEGORYMEMBERS
			assert map.get(ApiParams.KEY_CATEGORY_TITLE) == ApiParams.KEY_CATEGORY_TITLE_VALUE_PREFIX + TITLE_2
			assert map.get(ApiParams.KEY_CATEGORY_LIMIT) == ApiParams.KEY_CATEGORY_LIMIT_VALUE
			VALID_XML_WITH_PAGES_AND_CATEGORIES_2
		}
		1 * pageHeaderConverterMock.fromPageInfoList(_, MEDIA_WIKI_SOURCE) >> pageHeaderListInput1
		1 * pageHeaderConverterMock.fromPageInfoList(_, MEDIA_WIKI_SOURCE) >> pageHeaderListInput2
		0 * _
		pageHeaderListOutput.contains pageHeader1
		pageHeaderListOutput.contains pageHeader2
	}

	void "gets pages in categories by categories title"() {
		given:
		PageHeader pageHeader1 = Mock()
		PageHeader pageHeader2 = Mock()
		List<PageHeader> pageHeaderListInput1 = Lists.newArrayList(pageHeader1)
		List<PageHeader> pageHeaderListInput2 = Lists.newArrayList(pageHeader2)

		when:
		List<PageHeader> pageHeaderListOutput = categoryApiImpl.getPages(Lists.newArrayList(TITLE_1, TITLE_2), MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.readXML(_ as Map, _ as MediaWikiSource) >> { Map map, MediaWikiSource mediaWikiSource ->
			assert map.get(ApiParams.KEY_LIST) == ApiParams.KEY_LIST_VALUE_CATEGORYMEMBERS
			assert map.get(ApiParams.KEY_CATEGORY_TITLE) == ApiParams.KEY_CATEGORY_TITLE_VALUE_PREFIX + TITLE_1
			assert map.get(ApiParams.KEY_CATEGORY_LIMIT) == ApiParams.KEY_CATEGORY_LIMIT_VALUE
			VALID_XML
		}
		1 * blikiConnectorMock.readXML(_ as Map, _ as MediaWikiSource) >> { Map map, MediaWikiSource mediaWikiSource ->
			assert map.get(ApiParams.KEY_LIST) == ApiParams.KEY_LIST_VALUE_CATEGORYMEMBERS
			assert map.get(ApiParams.KEY_CATEGORY_TITLE) == ApiParams.KEY_CATEGORY_TITLE_VALUE_PREFIX + TITLE_2
			assert map.get(ApiParams.KEY_CATEGORY_LIMIT) == ApiParams.KEY_CATEGORY_LIMIT_VALUE
			VALID_XML_WITH_PAGES_AND_CATEGORIES_2
		}
		1 * pageHeaderConverterMock.fromPageInfoList(_, MEDIA_WIKI_SOURCE) >> pageHeaderListInput1
		1 * pageHeaderConverterMock.fromPageInfoList(_, MEDIA_WIKI_SOURCE) >> pageHeaderListInput2
		0 * _
		pageHeaderListOutput.contains pageHeader1
		pageHeaderListOutput.contains pageHeader2
	}

	void "follows cmcontinue attribute if continue tag is present"() {
		given:
		List<PageHeader> pageHeaderListInput = Lists.newArrayList()

		when:
		List<PageHeader> pageHeaderListOutput = categoryApiImpl.getPages(TITLE_1, MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.readXML(_ as Map, _ as MediaWikiSource) >> { Map map, MediaWikiSource mediaWikiSource ->
			assert map.size() == 3
			assert map.get(ApiParams.KEY_LIST) == ApiParams.KEY_LIST_VALUE_CATEGORYMEMBERS
			assert map.get(ApiParams.KEY_CATEGORY_TITLE) == ApiParams.KEY_CATEGORY_TITLE_VALUE_PREFIX + TITLE_1
			assert map.get(ApiParams.KEY_CATEGORY_LIMIT) == ApiParams.KEY_CATEGORY_LIMIT_VALUE
			VALID_XML_CONTINUE
		}
		1 * blikiConnectorMock.readXML(_ as Map, _ as MediaWikiSource) >> { Map map, MediaWikiSource mediaWikiSource ->
			assert map.size() == 4
			assert map.get(ApiParams.KEY_LIST) == ApiParams.KEY_LIST_VALUE_CATEGORYMEMBERS
			assert map.get(ApiParams.KEY_CATEGORY_TITLE) == ApiParams.KEY_CATEGORY_TITLE_VALUE_PREFIX + TITLE_1
			assert map.get(ApiParams.KEY_CATEGORY_LIMIT) == ApiParams.KEY_CATEGORY_LIMIT_VALUE
			assert map.get(ApiParams.KEY_CATEGORY_CONTINIUE) == CM_CONTINUE
			VALID_XML
		}
		1 * pageHeaderConverterMock.fromPageInfoList(_, MEDIA_WIKI_SOURCE) >> pageHeaderListInput
		0 * _
		pageHeaderListOutput == pageHeaderListInput
	}

	void "throws runtime exception when pages in category cannot be retrieved by title"() {
		given: 'invalid XML_1 is passed XMLCategoryMembersParser'
		blikiConnectorMock.readXML(_ as Map, _ as MediaWikiSource) >> INVALID_XML

		when:
		categoryApiImpl.getPages(TITLE_1, MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.readXML(_ as Map, _ as MediaWikiSource)
		0 * _
		thrown(StapiRuntimeException)
	}

	void "gets pages in category, including subcategories"() {
		given:
		List<PageHeader> pageHeaderList = Lists.newArrayList()

		when:
		List<PageHeader> pageHeaderListOutput = categoryApiImpl.getPagesIncludingSubcategories(TITLE_1, MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.readXML(_ as Map, _ as MediaWikiSource) >> VALID_XML_WITH_PAGES_AND_CATEGORIES_2
		1 * blikiConnectorMock.readXML(_ as Map, _ as MediaWikiSource) >> VALID_XML_WITH_PAGES_AND_PARENT_CATEGORY
		1 * pageHeaderConverterMock.fromPageInfoList(_ as List<PageInfo>, MEDIA_WIKI_SOURCE) >> { List<PageInfo> pageInfoList,
				MediaWikiSource mediaWikiSource ->
			assert pageInfoList.size() == 2
			List<String> titles = pageInfoList.stream()
					.map { pageHeader -> pageHeader.title }
					.collect(Collectors.toList())
			assert titles.contains(TITLE_2)
			assert titles.contains(TITLE_3)
			pageHeaderList
		}
		0 * _
		pageHeaderListOutput == pageHeaderList
	}

	void "gets pages in multiple categories, including subcategories"() {
		given:
		List<PageHeader> pageHeaderList = Lists.newArrayList()

		when:
		List<PageHeader> pageHeaderListOutput = categoryApiImpl
				.getPagesIncludingSubcategories(Lists.newArrayList(TITLE_1, TITLE_2), MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.readXML(_ as Map, _ as MediaWikiSource) >> VALID_XML_WITH_PAGES_AND_CATEGORIES_1
		1 * blikiConnectorMock.readXML(_ as Map, _ as MediaWikiSource) >> VALID_XML_WITH_PAGES_AND_PARENT_CATEGORY
		1 * blikiConnectorMock.readXML(_ as Map, _ as MediaWikiSource) >> VALID_XML_WITH_PAGES_AND_CATEGORIES_2
		1 * blikiConnectorMock.readXML(_ as Map, _ as MediaWikiSource) >> VALID_XML_WITH_PAGES_AND_PARENT_CATEGORY
		1 * pageHeaderConverterMock.fromPageInfoList(_ as List<PageInfo>, MEDIA_WIKI_SOURCE) >> { List<PageInfo> pageInfoList,
				MediaWikiSource mediaWikiSource ->
			assert pageInfoList.size() == 3
			List<String> titles = pageInfoList.stream()
					.map { pageHeader -> pageHeader.title }
					.collect(Collectors.toList())
			assert titles.contains(TITLE_1)
			assert titles.contains(TITLE_2)
			assert titles.contains(TITLE_3)
			pageHeaderList
		}
		0 * _
		pageHeaderListOutput == pageHeaderList
	}

	void "gets pages in multiple categories, including subcategories, with exceptions"() {
		given:
		List<PageHeader> pageHeaderList = Lists.newArrayList()

		when:
		List<PageHeader> pageHeaderListOutput = categoryApiImpl
				.getPagesIncludingSubcategoriesExcept(TITLE_1, Lists.newArrayList(TITLE_3), MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.readXML(_ as Map, _ as MediaWikiSource) >> VALID_XML_WITH_PAGES_AND_CATEGORIES_2
		1 * blikiConnectorMock.readXML(_ as Map, _ as MediaWikiSource) >> VALID_XML_WITH_PAGES_AND_CATEGORY_TO_EXCLUDE
		1 * pageHeaderConverterMock.fromPageInfoList(_ as List<PageInfo>, MEDIA_WIKI_SOURCE) >> { List<PageInfo> pageInfoList,
				MediaWikiSource mediaWikiSource ->
			assert pageInfoList.size() == 1
			List<String> titles = pageInfoList.stream()
					.map { pageHeader -> pageHeader.title }
					.collect(Collectors.toList())
			assert titles.contains(TITLE_2)
			pageHeaderList
		}
		0 * _
		pageHeaderListOutput == pageHeaderList
	}

	void "gets pages in multiple categories, including subcategories, with excludes"() {
		given:
		List<PageHeader> pageHeaderList = Lists.newArrayList()

		when:
		List<PageHeader> pageHeaderListOutput = categoryApiImpl
				.getPagesIncludingSubcategoriesExcluding(TITLE_1, Lists.newArrayList(Pattern.compile("(${TITLE_3}).*")), MEDIA_WIKI_SOURCE)

		then:
		1 * blikiConnectorMock.readXML(_ as Map, _ as MediaWikiSource) >> VALID_XML_WITH_PAGES_AND_CATEGORIES_2
		1 * blikiConnectorMock.readXML(_ as Map, _ as MediaWikiSource) >> VALID_XML_WITH_PAGES_AND_CATEGORY_TO_EXCLUDE
		1 * pageHeaderConverterMock.fromPageInfoList(_ as List<PageInfo>, MEDIA_WIKI_SOURCE) >> { List<PageInfo> pageInfoList,
				MediaWikiSource mediaWikiSource ->
			assert pageInfoList.size() == 1
			List<String> titles = pageInfoList.stream()
					.map { pageHeader -> pageHeader.title }
					.collect(Collectors.toList())
			assert titles.contains(TITLE_2)
			pageHeaderList
		}
		0 * _
		pageHeaderListOutput == pageHeaderList
	}

}
