package com.cezarykluczynski.stapi.server.material.query

import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.material.dto.MaterialRequestDTO
import com.cezarykluczynski.stapi.model.material.repository.MaterialRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.material.mapper.MaterialBaseSoapMapper
import com.cezarykluczynski.stapi.server.material.mapper.MaterialFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class MaterialSoapQueryTest extends Specification {

	private MaterialBaseSoapMapper materialBaseSoapMapperMock

	private MaterialFullSoapMapper materialFullSoapMapperMock

	private PageMapper pageMapperMock

	private MaterialRepository materialRepositoryMock

	private MaterialSoapQuery materialSoapQuery

	void setup() {
		materialBaseSoapMapperMock = Mock()
		materialFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		materialRepositoryMock = Mock()
		materialSoapQuery = new MaterialSoapQuery(materialBaseSoapMapperMock, materialFullSoapMapperMock, pageMapperMock, materialRepositoryMock)
	}

	void "maps MaterialBaseRequest to MaterialRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		MaterialBaseRequest materialRequest = Mock()
		materialRequest.page >> requestPage
		MaterialRequestDTO materialRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = materialSoapQuery.query(materialRequest)

		then:
		1 * materialBaseSoapMapperMock.mapBase(materialRequest) >> materialRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * materialRepositoryMock.findMatching(materialRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps MaterialFullRequest to MaterialRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		MaterialFullRequest materialRequest = Mock()
		MaterialRequestDTO materialRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = materialSoapQuery.query(materialRequest)

		then:
		1 * materialFullSoapMapperMock.mapFull(materialRequest) >> materialRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * materialRepositoryMock.findMatching(materialRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
