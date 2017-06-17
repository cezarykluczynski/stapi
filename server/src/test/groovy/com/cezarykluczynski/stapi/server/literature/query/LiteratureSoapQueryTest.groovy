package com.cezarykluczynski.stapi.server.literature.query

import com.cezarykluczynski.stapi.client.v1.soap.LiteratureBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.LiteratureFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.literature.dto.LiteratureRequestDTO
import com.cezarykluczynski.stapi.model.literature.repository.LiteratureRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureBaseSoapMapper
import com.cezarykluczynski.stapi.server.literature.mapper.LiteratureFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class LiteratureSoapQueryTest extends Specification {

	private LiteratureBaseSoapMapper literatureBaseSoapMapperMock

	private LiteratureFullSoapMapper literatureFullSoapMapperMock

	private PageMapper pageMapperMock

	private LiteratureRepository literatureRepositoryMock

	private LiteratureSoapQuery literatureSoapQuery

	void setup() {
		literatureBaseSoapMapperMock = Mock()
		literatureFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		literatureRepositoryMock = Mock()
		literatureSoapQuery = new LiteratureSoapQuery(literatureBaseSoapMapperMock, literatureFullSoapMapperMock, pageMapperMock,
				literatureRepositoryMock)
	}

	void "maps LiteratureBaseRequest to LiteratureRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		LiteratureBaseRequest literatureRequest = Mock()
		literatureRequest.page >> requestPage
		LiteratureRequestDTO literatureRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = literatureSoapQuery.query(literatureRequest)

		then:
		1 * literatureBaseSoapMapperMock.mapBase(literatureRequest) >> literatureRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * literatureRepositoryMock.findMatching(literatureRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps LiteratureFullRequest to LiteratureRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		LiteratureFullRequest literatureRequest = Mock()
		LiteratureRequestDTO literatureRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = literatureSoapQuery.query(literatureRequest)

		then:
		1 * literatureFullSoapMapperMock.mapFull(literatureRequest) >> literatureRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * literatureRepositoryMock.findMatching(literatureRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
