package com.cezarykluczynski.stapi.server.technology.query

import com.cezarykluczynski.stapi.client.v1.soap.TechnologyBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TechnologyFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.technology.dto.TechnologyRequestDTO
import com.cezarykluczynski.stapi.model.technology.repository.TechnologyRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyBaseSoapMapper
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class TechnologySoapQueryTest extends Specification {

	private TechnologyBaseSoapMapper technologyBaseSoapMapperMock

	private TechnologyFullSoapMapper technologyFullSoapMapperMock

	private PageMapper pageMapperMock

	private TechnologyRepository technologyRepositoryMock

	private TechnologySoapQuery technologySoapQuery

	void setup() {
		technologyBaseSoapMapperMock = Mock()
		technologyFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		technologyRepositoryMock = Mock()
		technologySoapQuery = new TechnologySoapQuery(technologyBaseSoapMapperMock, technologyFullSoapMapperMock, pageMapperMock,
				technologyRepositoryMock)
	}

	void "maps TechnologyBaseRequest to TechnologyRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		TechnologyBaseRequest technologyRequest = Mock()
		technologyRequest.page >> requestPage
		TechnologyRequestDTO technologyRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = technologySoapQuery.query(technologyRequest)

		then:
		1 * technologyBaseSoapMapperMock.mapBase(technologyRequest) >> technologyRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * technologyRepositoryMock.findMatching(technologyRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps TechnologyFullRequest to TechnologyRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		TechnologyFullRequest technologyRequest = Mock()
		TechnologyRequestDTO technologyRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = technologySoapQuery.query(technologyRequest)

		then:
		1 * technologyFullSoapMapperMock.mapFull(technologyRequest) >> technologyRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * technologyRepositoryMock.findMatching(technologyRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
