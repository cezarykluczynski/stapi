package com.cezarykluczynski.stapi.server.species.query

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.SpeciesFullRequest
import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseSoapMapper
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class SpeciesSoapQueryTest extends Specification {

	private SpeciesBaseSoapMapper speciesBaseSoapMapperMock

	private SpeciesFullSoapMapper speciesFullSoapMapperMock

	private PageMapper pageMapperMock

	private SpeciesRepository speciesRepositoryMock

	private SpeciesSoapQuery speciesSoapQuery

	void setup() {
		speciesBaseSoapMapperMock = Mock()
		speciesFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		speciesRepositoryMock = Mock()
		speciesSoapQuery = new SpeciesSoapQuery(speciesBaseSoapMapperMock, speciesFullSoapMapperMock, pageMapperMock, speciesRepositoryMock)
	}

	void "maps SpeciesBaseRequest to SpeciesRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		SpeciesBaseRequest speciesRequest = Mock()
		speciesRequest.page >> requestPage
		SpeciesRequestDTO speciesRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = speciesSoapQuery.query(speciesRequest)

		then:
		1 * speciesBaseSoapMapperMock.mapBase(speciesRequest) >> speciesRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * speciesRepositoryMock.findMatching(speciesRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps SpeciesFullRequest to SpeciesRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		SpeciesFullRequest speciesRequest = Mock()
		SpeciesRequestDTO speciesRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = speciesSoapQuery.query(speciesRequest)

		then:
		1 * speciesFullSoapMapperMock.mapFull(speciesRequest) >> speciesRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * speciesRepositoryMock.findMatching(speciesRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
