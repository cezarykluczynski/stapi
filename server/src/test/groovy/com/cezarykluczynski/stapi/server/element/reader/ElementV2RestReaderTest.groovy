package com.cezarykluczynski.stapi.server.element.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ElementV2Base
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementV2Full
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementV2FullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.element.entity.Element
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.element.dto.ElementV2RestBeanParams
import com.cezarykluczynski.stapi.server.element.mapper.ElementBaseRestMapper
import com.cezarykluczynski.stapi.server.element.mapper.ElementFullRestMapper
import com.cezarykluczynski.stapi.server.element.query.ElementRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ElementV2RestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private ElementRestQuery elementRestQueryBuilderMock

	private ElementBaseRestMapper elementBaseRestMapperMock

	private ElementFullRestMapper elementFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private ElementV2RestReader elementV2RestReader

	void setup() {
		elementRestQueryBuilderMock = Mock()
		elementBaseRestMapperMock = Mock()
		elementFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		elementV2RestReader = new ElementV2RestReader(elementRestQueryBuilderMock, elementBaseRestMapperMock, elementFullRestMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		ElementV2Base elementV2Base = Mock()
		Element element = Mock()
		ElementV2RestBeanParams elementV2RestBeanParams = Mock()
		List<ElementV2Base> restElementList = Lists.newArrayList(elementV2Base)
		List<Element> elementList = Lists.newArrayList(element)
		Page<Element> elementPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		ElementV2BaseResponse elementResponseOutput = elementV2RestReader.readBase(elementV2RestBeanParams)

		then:
		1 * elementRestQueryBuilderMock.query(elementV2RestBeanParams) >> elementPage
		1 * pageMapperMock.fromPageToRestResponsePage(elementPage) >> responsePage
		1 * elementV2RestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * elementPage.content >> elementList
		1 * elementBaseRestMapperMock.mapV2Base(elementList) >> restElementList
		0 * _
		elementResponseOutput.elements == restElementList
		elementResponseOutput.page == responsePage
		elementResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		ElementV2Full elementV2Full = Mock()
		Element element = Mock()
		List<Element> elementList = Lists.newArrayList(element)
		Page<Element> elementPage = Mock()

		when:
		ElementV2FullResponse elementResponseOutput = elementV2RestReader.readFull(UID)

		then:
		1 * elementRestQueryBuilderMock.query(_ as ElementV2RestBeanParams) >> { ElementV2RestBeanParams elementV2RestBeanParams ->
			assert elementV2RestBeanParams.uid == UID
			elementPage
		}
		1 * elementPage.content >> elementList
		1 * elementFullRestMapperMock.mapV2Full(element) >> elementV2Full
		0 * _
		elementResponseOutput.element == elementV2Full
	}

	void "requires UID in full request"() {
		when:
		elementV2RestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
