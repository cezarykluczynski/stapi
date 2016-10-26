package com.cezarykluczynski.stapi.sources.mediawiki.api

import com.cezarykluczynski.stapi.sources.mediawiki.util.constant.ApiParams
import com.cezarykluczynski.stapi.sources.mediawiki.connector.bliki.BlikiConnector
import com.cezarykluczynski.stapi.sources.mediawiki.converter.PageHeaderConverter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class CategoryApiImplTest extends Specification {

	private static final String TITLE = 'TITLE_1'
	private static final String CM_CONTINUE = "abc"
	private static final String VALID_XML = """<?xml version="1.0"?>
		<api batchcomplete="">
			<query>
				<categorymembers>
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
		pageHeaderConverterMock.fromPageInfoList(_) >> pageHeaderListInput

		when:
		List<PageHeader> pageHeaderListOutput = categoryApiImpl.getPages(TITLE)

		then:
		1 * blikiConnectorMock.readXML(_ as Map) >> { Map map ->
			assert map.get(ApiParams.KEY_LIST) == ApiParams.KEY_LIST_VALUE_CATEGORYMEMBERS
			assert map.get(ApiParams.KEY_CATEGORY_TITLE) == ApiParams.KEY_CATEGORY_TITLE_VALUE_PREFIX + TITLE
			assert map.get(ApiParams.KEY_CATEGORY_LIMIT) == ApiParams.KEY_CATEGORY_LIMIT_VALUE
			return VALID_XML
		}
		pageHeaderListOutput == pageHeaderListInput
	}

	def "follows cmcontinue attribute if continue tag is present"() {
		given:
		List<PageHeader> pageHeaderListInput = Lists.newArrayList()
		pageHeaderConverterMock.fromPageInfoList(_) >> pageHeaderListInput

		when:
		List<PageHeader> pageHeaderListOutput = categoryApiImpl.getPages(TITLE)

		then:
		1 * blikiConnectorMock.readXML(_ as Map) >> { Map map ->
			assert map.size() == 3
			assert map.get(ApiParams.KEY_LIST) == ApiParams.KEY_LIST_VALUE_CATEGORYMEMBERS
			assert map.get(ApiParams.KEY_CATEGORY_TITLE) == ApiParams.KEY_CATEGORY_TITLE_VALUE_PREFIX + TITLE
			assert map.get(ApiParams.KEY_CATEGORY_LIMIT) == ApiParams.KEY_CATEGORY_LIMIT_VALUE
			return VALID_XML_CONTINUE
		}
		1 * blikiConnectorMock.readXML(_ as Map) >> { Map map ->
			assert map.size() == 4
			assert map.get(ApiParams.KEY_LIST) == ApiParams.KEY_LIST_VALUE_CATEGORYMEMBERS
			assert map.get(ApiParams.KEY_CATEGORY_TITLE) == ApiParams.KEY_CATEGORY_TITLE_VALUE_PREFIX + TITLE
			assert map.get(ApiParams.KEY_CATEGORY_LIMIT) == ApiParams.KEY_CATEGORY_LIMIT_VALUE
			assert map.get(ApiParams.KEY_CATEGORY_CONTINIUE) == CM_CONTINUE
			return VALID_XML
		}

		pageHeaderListOutput == pageHeaderListInput
	}

	def "throws runtime exception when pages in category cannot be retrieved by title"() {
		given: "invalid XML_1 is passed XMLCategoryMembersParser"
		blikiConnectorMock.readXML(_ as Map) >> INVALID_XML

		when:
		categoryApiImpl.getPages(TITLE)

		then:
		thrown(RuntimeException)
	}

}
