package com.cezarykluczynski.stapi.server.title.query

import com.cezarykluczynski.stapi.client.v1.soap.TitleBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TitleFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.title.dto.TitleRequestDTO
import com.cezarykluczynski.stapi.model.title.repository.TitleRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.title.mapper.TitleBaseSoapMapper
import com.cezarykluczynski.stapi.server.title.mapper.TitleFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class TitleSoapQueryTest extends Specification {

	private TitleBaseSoapMapper titleBaseSoapMapperMock

	private TitleFullSoapMapper titleFullSoapMapperMock

	private PageMapper pageMapperMock

	private TitleRepository titleRepositoryMock

	private TitleSoapQuery titleSoapQuery

	void setup() {
		titleBaseSoapMapperMock = Mock()
		titleFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		titleRepositoryMock = Mock()
		titleSoapQuery = new TitleSoapQuery(titleBaseSoapMapperMock, titleFullSoapMapperMock, pageMapperMock, titleRepositoryMock)
	}

	void "maps TitleBaseRequest to TitleRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		TitleBaseRequest titleRequest = Mock()
		titleRequest.page >> requestPage
		TitleRequestDTO titleRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = titleSoapQuery.query(titleRequest)

		then:
		1 * titleBaseSoapMapperMock.mapBase(titleRequest) >> titleRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * titleRepositoryMock.findMatching(titleRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps TitleFullRequest to TitleRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		TitleFullRequest titleRequest = Mock()
		TitleRequestDTO titleRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = titleSoapQuery.query(titleRequest)

		then:
		1 * titleFullSoapMapperMock.mapFull(titleRequest) >> titleRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * titleRepositoryMock.findMatching(titleRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
