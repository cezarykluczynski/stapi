package com.cezarykluczynski.stapi.wiki.api

import com.cezarykluczynski.stapi.util.constants.ApiParams
import com.cezarykluczynski.stapi.wiki.connector.bliki.BlikiConnector
import com.cezarykluczynski.stapi.wiki.converter.PageHeaderConverter
import com.cezarykluczynski.stapi.wiki.dto.PageHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class CategoryApiImplTest extends Specification {

	private static final String TITLE = 'TITLE'
	private static final String VALID_XML = """<?xml version="1.0"?>
		<api batchcomplete="">
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
			return VALID_XML
		}
		pageHeaderListOutput == pageHeaderListInput
	}

	def "throws runtime exception when pages in category cannot be retrieved by title"() {
		given: "invalid XML is passed XMLCategoryMembersParser"
		blikiConnectorMock.readXML(_ as Map) >> INVALID_XML

		when:
		categoryApiImpl.getPages(TITLE)

		then:
		thrown(RuntimeException)
	}

}
