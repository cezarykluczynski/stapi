package com.cezarykluczynski.stapi.server.element.reader

import com.cezarykluczynski.stapi.client.v1.soap.ElementBase
import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.ElementFull
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.element.entity.Element
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.element.mapper.ElementBaseSoapMapper
import com.cezarykluczynski.stapi.server.element.mapper.ElementFullSoapMapper
import com.cezarykluczynski.stapi.server.element.query.ElementSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class ElementSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private ElementSoapQuery elementSoapQueryBuilderMock

	private ElementBaseSoapMapper elementBaseSoapMapperMock

	private ElementFullSoapMapper elementFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private ElementSoapReader elementSoapReader

	void setup() {
		elementSoapQueryBuilderMock = Mock()
		elementBaseSoapMapperMock = Mock()
		elementFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		elementSoapReader = new ElementSoapReader(elementSoapQueryBuilderMock, elementBaseSoapMapperMock, elementFullSoapMapperMock, pageMapperMock,
				sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Element> elementList = Lists.newArrayList()
		Page<Element> elementPage = Mock()
		List<ElementBase> soapElementList = Lists.newArrayList(new ElementBase(uid: UID))
		ElementBaseRequest elementBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		ElementBaseResponse elementResponse = elementSoapReader.readBase(elementBaseRequest)

		then:
		1 * elementSoapQueryBuilderMock.query(elementBaseRequest) >> elementPage
		1 * elementPage.content >> elementList
		1 * pageMapperMock.fromPageToSoapResponsePage(elementPage) >> responsePage
		1 * elementBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * elementBaseSoapMapperMock.mapBase(elementList) >> soapElementList
		0 * _
		elementResponse.elements[0].uid == UID
		elementResponse.page == responsePage
		elementResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		ElementFull elementFull = new ElementFull(uid: UID)
		Element element = Mock()
		Page<Element> elementPage = Mock()
		ElementFullRequest elementFullRequest = new ElementFullRequest(uid: UID)

		when:
		ElementFullResponse elementFullResponse = elementSoapReader.readFull(elementFullRequest)

		then:
		1 * elementSoapQueryBuilderMock.query(elementFullRequest) >> elementPage
		1 * elementPage.content >> Lists.newArrayList(element)
		1 * elementFullSoapMapperMock.mapFull(element) >> elementFull
		0 * _
		elementFullResponse.element.uid == UID
	}

	void "requires UID in full request"() {
		given:
		ElementFullRequest elementFullRequest = Mock()

		when:
		elementSoapReader.readFull(elementFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
