package com.cezarykluczynski.stapi.server.technology.query

import com.cezarykluczynski.stapi.model.technology.dto.TechnologyRequestDTO
import com.cezarykluczynski.stapi.model.technology.repository.TechnologyRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.technology.dto.TechnologyRestBeanParams
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class TechnologyRestQueryTest extends Specification {

	private TechnologyBaseRestMapper technologyRestMapperMock

	private PageMapper pageMapperMock

	private TechnologyRepository technologyRepositoryMock

	private TechnologyRestQuery technologyRestQuery

	void setup() {
		technologyRestMapperMock = Mock()
		pageMapperMock = Mock()
		technologyRepositoryMock = Mock()
		technologyRestQuery = new TechnologyRestQuery(technologyRestMapperMock, pageMapperMock, technologyRepositoryMock)
	}

	void "maps TechnologyRestBeanParams to TechnologyRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		TechnologyRestBeanParams technologyRestBeanParams = Mock()
		TechnologyRequestDTO technologyRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = technologyRestQuery.query(technologyRestBeanParams)

		then:
		1 * technologyRestMapperMock.mapBase(technologyRestBeanParams) >> technologyRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(technologyRestBeanParams) >> pageRequest
		1 * technologyRepositoryMock.findMatching(technologyRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
