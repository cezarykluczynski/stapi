package com.cezarykluczynski.stapi.server.performer.query

import com.cezarykluczynski.stapi.client.v1.soap.PerformerBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.PerformerFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerBaseSoapMapper
import com.cezarykluczynski.stapi.server.performer.mapper.PerformerFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class PerformerSoapQueryTest extends Specification {

	private PerformerBaseSoapMapper performerBaseSoapMapperMock

	private PerformerFullSoapMapper performerFullSoapMapperMock

	private PageMapper pageMapperMock

	private PerformerRepository performerRepositoryMock

	private PerformerSoapQuery performerSoapQuery

	void setup() {
		performerBaseSoapMapperMock = Mock()
		performerFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		performerRepositoryMock = Mock()
		performerSoapQuery = new PerformerSoapQuery(performerBaseSoapMapperMock, performerFullSoapMapperMock, pageMapperMock, performerRepositoryMock)
	}

	void "maps PerformerBaseRequest to PerformerRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		PerformerBaseRequest performerRequest = Mock()
		performerRequest.page >> requestPage
		PerformerRequestDTO performerRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = performerSoapQuery.query(performerRequest)

		then:
		1 * performerBaseSoapMapperMock.mapBase(performerRequest) >> performerRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * performerRepositoryMock.findMatching(performerRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps PerformerFullRequest to PerformerRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		PerformerFullRequest performerRequest = Mock()
		PerformerRequestDTO performerRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = performerSoapQuery.query(performerRequest)

		then:
		1 * performerFullSoapMapperMock.mapFull(performerRequest) >> performerRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * performerRepositoryMock.findMatching(performerRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
