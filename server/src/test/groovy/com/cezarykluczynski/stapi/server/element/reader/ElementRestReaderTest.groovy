package com.cezarykluczynski.stapi.server.element.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ElementBase
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementFull
import com.cezarykluczynski.stapi.client.v1.rest.model.ElementFullResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.model.element.entity.Element
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.element.dto.ElementRestBeanParams
import com.cezarykluczynski.stapi.server.element.mapper.ElementBaseRestMapper
import com.cezarykluczynski.stapi.server.element.mapper.ElementFullRestMapper
import com.cezarykluczynski.stapi.server.element.query.ElementRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ElementRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private ElementRestQuery elementRestQueryBuilderMock

	private ElementBaseRestMapper elementBaseRestMapperMock

	private ElementFullRestMapper elementFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private ElementRestReader elementRestReader

	void setup() {
		elementRestQueryBuilderMock = Mock()
		elementBaseRestMapperMock = Mock()
		elementFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		elementRestReader = new ElementRestReader(elementRestQueryBuilderMock, elementBaseRestMapperMock, elementFullRestMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		ElementBase elementBase = Mock()
		Element element = Mock()
		ElementRestBeanParams elementRestBeanParams = Mock()
		List<ElementBase> restElementList = Lists.newArrayList(elementBase)
		List<Element> elementList = Lists.newArrayList(element)
		Page<Element> elementPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		ElementBaseResponse elementResponseOutput = elementRestReader.readBase(elementRestBeanParams)

		then:
		1 * elementRestQueryBuilderMock.query(elementRestBeanParams) >> elementPage
		1 * pageMapperMock.fromPageToRestResponsePage(elementPage) >> responsePage
		1 * elementRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * elementPage.content >> elementList
		1 * elementBaseRestMapperMock.mapBase(elementList) >> restElementList
		0 * _
		elementResponseOutput.elements == restElementList
		elementResponseOutput.page == responsePage
		elementResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		ElementFull elementFull = Mock()
		Element element = Mock()
		List<Element> elementList = Lists.newArrayList(element)
		Page<Element> elementPage = Mock()

		when:
		ElementFullResponse elementResponseOutput = elementRestReader.readFull(UID)

		then:
		1 * elementRestQueryBuilderMock.query(_ as ElementRestBeanParams) >> { ElementRestBeanParams elementRestBeanParams ->
			assert elementRestBeanParams.uid == UID
			elementPage
		}
		1 * elementPage.content >> elementList
		1 * elementFullRestMapperMock.mapFull(element) >> elementFull
		0 * _
		elementResponseOutput.element == elementFull
	}

	void "requires UID in full request"() {
		when:
		elementRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
