package com.cezarykluczynski.stapi.server.spacecraft_class.query

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullRequest
import com.cezarykluczynski.stapi.model.spacecraft_class.dto.SpacecraftClassRequestDTO
import com.cezarykluczynski.stapi.model.spacecraft_class.repository.SpacecraftClassRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassBaseSoapMapper
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class SpacecraftClassSoapQueryTest extends Specification {

	private SpacecraftClassBaseSoapMapper spacecraftClassBaseSoapMapperMock

	private SpacecraftClassFullSoapMapper spacecraftClassFullSoapMapperMock

	private PageMapper pageMapperMock

	private SpacecraftClassRepository spacecraftClassRepositoryMock

	private SpacecraftClassSoapQuery spacecraftClassSoapQuery

	void setup() {
		spacecraftClassBaseSoapMapperMock = Mock()
		spacecraftClassFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		spacecraftClassRepositoryMock = Mock()
		spacecraftClassSoapQuery = new SpacecraftClassSoapQuery(spacecraftClassBaseSoapMapperMock, spacecraftClassFullSoapMapperMock, pageMapperMock,
				spacecraftClassRepositoryMock)
	}

	void "maps SpacecraftClassBaseRequest to SpacecraftClassRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		SpacecraftClassBaseRequest spacecraftClassRequest = Mock()
		spacecraftClassRequest.page >> requestPage
		SpacecraftClassRequestDTO spacecraftClassRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = spacecraftClassSoapQuery.query(spacecraftClassRequest)

		then:
		1 * spacecraftClassBaseSoapMapperMock.mapBase(spacecraftClassRequest) >> spacecraftClassRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * spacecraftClassRepositoryMock.findMatching(spacecraftClassRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps SpacecraftClassFullRequest to SpacecraftClassRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		SpacecraftClassFullRequest spacecraftClassRequest = Mock()
		SpacecraftClassRequestDTO spacecraftClassRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = spacecraftClassSoapQuery.query(spacecraftClassRequest)

		then:
		1 * spacecraftClassFullSoapMapperMock.mapFull(spacecraftClassRequest) >> spacecraftClassRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * spacecraftClassRepositoryMock.findMatching(spacecraftClassRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
