package com.cezarykluczynski.stapi.server.material.query

import com.cezarykluczynski.stapi.model.material.dto.MaterialRequestDTO
import com.cezarykluczynski.stapi.model.material.repository.MaterialRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.material.dto.MaterialRestBeanParams
import com.cezarykluczynski.stapi.server.material.mapper.MaterialBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class MaterialRestQueryTest extends Specification {

	private MaterialBaseRestMapper materialRestMapperMock

	private PageMapper pageMapperMock

	private MaterialRepository materialRepositoryMock

	private MaterialRestQuery materialRestQuery

	void setup() {
		materialRestMapperMock = Mock()
		pageMapperMock = Mock()
		materialRepositoryMock = Mock()
		materialRestQuery = new MaterialRestQuery(materialRestMapperMock, pageMapperMock, materialRepositoryMock)
	}

	void "maps MaterialRestBeanParams to MaterialRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		MaterialRestBeanParams materialRestBeanParams = Mock()
		MaterialRequestDTO materialRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = materialRestQuery.query(materialRestBeanParams)

		then:
		1 * materialRestMapperMock.mapBase(materialRestBeanParams) >> materialRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(materialRestBeanParams) >> pageRequest
		1 * materialRepositoryMock.findMatching(materialRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
