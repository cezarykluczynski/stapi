package com.cezarykluczynski.stapi.server.technology.query

import com.cezarykluczynski.stapi.model.technology.dto.TechnologyRequestDTO
import com.cezarykluczynski.stapi.model.technology.repository.TechnologyRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.technology.dto.TechnologyRestBeanParams
import com.cezarykluczynski.stapi.server.technology.dto.TechnologyV2RestBeanParams
import com.cezarykluczynski.stapi.server.technology.mapper.TechnologyBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class TechnologyRestQueryTest extends Specification {

	private TechnologyBaseRestMapper technologyBaseRestMapperMock

	private PageMapper pageMapperMock

	private TechnologyRepository technologyRepositoryMock

	private TechnologyRestQuery technologyRestQuery

	void setup() {
		technologyBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		technologyRepositoryMock = Mock()
		technologyRestQuery = new TechnologyRestQuery(technologyBaseRestMapperMock, pageMapperMock, technologyRepositoryMock)
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
		1 * technologyBaseRestMapperMock.mapBase(technologyRestBeanParams) >> technologyRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(technologyRestBeanParams) >> pageRequest
		1 * technologyRepositoryMock.findMatching(technologyRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps TechnologyV2RestBeanParams to TechnologyRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		TechnologyV2RestBeanParams technologyV2RestBeanParams = Mock()
		TechnologyRequestDTO technologyRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = technologyRestQuery.query(technologyV2RestBeanParams)

		then:
		1 * technologyBaseRestMapperMock.mapV2Base(technologyV2RestBeanParams) >> technologyRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(technologyV2RestBeanParams) >> pageRequest
		1 * technologyRepositoryMock.findMatching(technologyRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
