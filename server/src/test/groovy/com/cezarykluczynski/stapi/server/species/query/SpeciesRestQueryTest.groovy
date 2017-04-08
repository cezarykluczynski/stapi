package com.cezarykluczynski.stapi.server.species.query

import com.cezarykluczynski.stapi.model.species.dto.SpeciesRequestDTO
import com.cezarykluczynski.stapi.model.species.repository.SpeciesRepository
import com.cezarykluczynski.stapi.server.species.dto.SpeciesRestBeanParams
import com.cezarykluczynski.stapi.server.species.mapper.SpeciesBaseRestMapper
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class SpeciesRestQueryTest extends Specification {

	private SpeciesBaseRestMapper speciesRestMapperMock

	private PageMapper pageMapperMock

	private SpeciesRepository speciesRepositoryMock

	private SpeciesRestQuery speciesRestQuery

	void setup() {
		speciesRestMapperMock = Mock()
		pageMapperMock = Mock()
		speciesRepositoryMock = Mock()
		speciesRestQuery = new SpeciesRestQuery(speciesRestMapperMock, pageMapperMock, speciesRepositoryMock)
	}

	void "maps SpeciesRestBeanParams to SpeciesRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		SpeciesRestBeanParams speciesRestBeanParams = Mock()
		SpeciesRequestDTO speciesRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = speciesRestQuery.query(speciesRestBeanParams)

		then:
		1 * speciesRestMapperMock.mapBase(speciesRestBeanParams) >> speciesRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(speciesRestBeanParams) >> pageRequest
		1 * speciesRepositoryMock.findMatching(speciesRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
