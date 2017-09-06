package com.cezarykluczynski.stapi.server.spacecraft.query

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullRequest
import com.cezarykluczynski.stapi.model.spacecraft.dto.SpacecraftRequestDTO
import com.cezarykluczynski.stapi.model.spacecraft.repository.SpacecraftRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseSoapMapper
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class SpacecraftSoapQueryTest extends Specification {

	private SpacecraftBaseSoapMapper spacecraftBaseSoapMapperMock

	private SpacecraftFullSoapMapper spacecraftFullSoapMapperMock

	private PageMapper pageMapperMock

	private SpacecraftRepository spacecraftRepositoryMock

	private SpacecraftSoapQuery spacecraftSoapQuery

	void setup() {
		spacecraftBaseSoapMapperMock = Mock()
		spacecraftFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		spacecraftRepositoryMock = Mock()
		spacecraftSoapQuery = new SpacecraftSoapQuery(spacecraftBaseSoapMapperMock, spacecraftFullSoapMapperMock, pageMapperMock,
				spacecraftRepositoryMock)
	}

	void "maps SpacecraftBaseRequest to SpacecraftRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		SpacecraftBaseRequest spacecraftRequest = Mock()
		spacecraftRequest.page >> requestPage
		SpacecraftRequestDTO spacecraftRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = spacecraftSoapQuery.query(spacecraftRequest)

		then:
		1 * spacecraftBaseSoapMapperMock.mapBase(spacecraftRequest) >> spacecraftRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * spacecraftRepositoryMock.findMatching(spacecraftRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps SpacecraftFullRequest to SpacecraftRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		SpacecraftFullRequest spacecraftRequest = Mock()
		SpacecraftRequestDTO spacecraftRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = spacecraftSoapQuery.query(spacecraftRequest)

		then:
		1 * spacecraftFullSoapMapperMock.mapFull(spacecraftRequest) >> spacecraftRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * spacecraftRepositoryMock.findMatching(spacecraftRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
