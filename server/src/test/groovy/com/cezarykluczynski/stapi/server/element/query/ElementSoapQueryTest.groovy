package com.cezarykluczynski.stapi.server.element.query

import com.cezarykluczynski.stapi.client.v1.soap.ElementBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ElementFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.element.dto.ElementRequestDTO
import com.cezarykluczynski.stapi.model.element.repository.ElementRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.element.mapper.ElementBaseSoapMapper
import com.cezarykluczynski.stapi.server.element.mapper.ElementFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class ElementSoapQueryTest extends Specification {

	private ElementBaseSoapMapper elementBaseSoapMapperMock

	private ElementFullSoapMapper elementFullSoapMapperMock

	private PageMapper pageMapperMock

	private ElementRepository elementRepositoryMock

	private ElementSoapQuery elementSoapQuery

	void setup() {
		elementBaseSoapMapperMock = Mock()
		elementFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		elementRepositoryMock = Mock()
		elementSoapQuery = new ElementSoapQuery(elementBaseSoapMapperMock, elementFullSoapMapperMock, pageMapperMock, elementRepositoryMock)
	}

	void "maps ElementBaseRequest to ElementRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		ElementBaseRequest elementRequest = Mock()
		elementRequest.page >> requestPage
		ElementRequestDTO elementRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = elementSoapQuery.query(elementRequest)

		then:
		1 * elementBaseSoapMapperMock.mapBase(elementRequest) >> elementRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * elementRepositoryMock.findMatching(elementRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps ElementFullRequest to ElementRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		ElementFullRequest elementRequest = Mock()
		ElementRequestDTO elementRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = elementSoapQuery.query(elementRequest)

		then:
		1 * elementFullSoapMapperMock.mapFull(elementRequest) >> elementRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * elementRepositoryMock.findMatching(elementRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
