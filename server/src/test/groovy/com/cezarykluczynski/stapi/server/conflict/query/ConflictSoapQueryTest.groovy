package com.cezarykluczynski.stapi.server.conflict.query

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.ConflictBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.ConflictFullRequest
import com.cezarykluczynski.stapi.model.conflict.dto.ConflictRequestDTO
import com.cezarykluczynski.stapi.model.conflict.repository.ConflictRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictBaseSoapMapper
import com.cezarykluczynski.stapi.server.conflict.mapper.ConflictFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class ConflictSoapQueryTest extends Specification {

	private ConflictBaseSoapMapper conflictBaseSoapMapperMock

	private ConflictFullSoapMapper conflictFullSoapMapperMock

	private PageMapper pageMapperMock

	private ConflictRepository conflictRepositoryMock

	private ConflictSoapQuery conflictSoapQuery

	void setup() {
		conflictBaseSoapMapperMock = Mock()
		conflictFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		conflictRepositoryMock = Mock()
		conflictSoapQuery = new ConflictSoapQuery(conflictBaseSoapMapperMock, conflictFullSoapMapperMock, pageMapperMock,
				conflictRepositoryMock)
	}

	void "maps ConflictBaseRequest to ConflictRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		ConflictBaseRequest conflictRequest = Mock()
		conflictRequest.page >> requestPage
		ConflictRequestDTO conflictRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = conflictSoapQuery.query(conflictRequest)

		then:
		1 * conflictBaseSoapMapperMock.mapBase(conflictRequest) >> conflictRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * conflictRepositoryMock.findMatching(conflictRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps ConflictFullRequest to ConflictRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		ConflictFullRequest conflictRequest = Mock()
		ConflictRequestDTO conflictRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = conflictSoapQuery.query(conflictRequest)

		then:
		1 * conflictFullSoapMapperMock.mapFull(conflictRequest) >> conflictRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * conflictRepositoryMock.findMatching(conflictRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
