package com.cezarykluczynski.stapi.server.species.query

import com.cezarykluczynski.stapi.client.v1.soap.SpeciesRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesSoapMapper
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class SpeciesSoapQueryTest extends Specification {

	private SpeciesSoapMapper speciesSoapMapperMock

	private PageMapper pageMapperMock

	private SpeciesRepository speciesRepositoryMock

	private SpeciesSoapQuery speciesSoapQuery

	void setup() {
		speciesSoapMapperMock = Mock(SpeciesSoapMapper)
		pageMapperMock = Mock(PageMapper)
		speciesRepositoryMock = Mock(SpeciesRepository)
		speciesSoapQuery = new SpeciesSoapQuery(speciesSoapMapperMock, pageMapperMock, speciesRepositoryMock)
	}

	void "maps SpeciesRequest to SpeciesRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock(RequestPage)
		PageRequest pageRequest = Mock(PageRequest)
		SpeciesRequest speciesRequest = Mock(SpeciesRequest)
		speciesRequest.page >> requestPage
		SpeciesRequestDTO speciesRequestDTO = Mock(SpeciesRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = speciesSoapQuery.query(speciesRequest)

		then:
		1 * speciesSoapMapperMock.map(speciesRequest) >> speciesRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * speciesRepositoryMock.findMatching(speciesRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
