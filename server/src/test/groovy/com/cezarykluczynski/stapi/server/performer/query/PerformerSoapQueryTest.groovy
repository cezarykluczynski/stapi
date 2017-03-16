package com.cezarykluczynski.stapi.server.performer.query

import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class PerformerSoapQueryTest extends Specification {

	private PerformerSoapMapper performerSoapMapperMock

	private PageMapper pageMapperMock

	private PerformerRepository performerRepositoryMock

	private PerformerSoapQuery performerSoapQuery

	void setup() {
		performerSoapMapperMock = Mock(PerformerSoapMapper)
		pageMapperMock = Mock(PageMapper)
		performerRepositoryMock = Mock(PerformerRepository)
		performerSoapQuery = new PerformerSoapQuery(performerSoapMapperMock, pageMapperMock, performerRepositoryMock)
	}

	void "maps PerformerBaseRequest to PerformerRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		PerformerBaseRequest performerRequest = Mock(PerformerBaseRequest)
		performerRequest.page >> requestPage
		PerformerRequestDTO performerRequestDTO = Mock(PerformerRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = performerSoapQuery.query(performerRequest)

		then:
		1 * performerSoapMapperMock.mapBase(performerRequest) >> performerRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * performerRepositoryMock.findMatching(performerRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps PerformerFullRequest to PerformerRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock(PageRequest)
		PerformerFullRequest performerRequest = Mock(PerformerFullRequest)
		PerformerRequestDTO performerRequestDTO = Mock(PerformerRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = performerSoapQuery.query(performerRequest)

		then:
		1 * performerSoapMapperMock.mapFull(performerRequest) >> performerRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * performerRepositoryMock.findMatching(performerRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
