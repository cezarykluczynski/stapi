package com.cezarykluczynski.stapi.server.element.query

import com.cezarykluczynski.stapi.model.element.dto.ElementRequestDTO
import com.cezarykluczynski.stapi.model.element.repository.ElementRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.element.dto.ElementRestBeanParams
import com.cezarykluczynski.stapi.server.element.mapper.ElementBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class ElementRestQueryTest extends Specification {

	private ElementBaseRestMapper elementRestMapperMock

	private PageMapper pageMapperMock

	private ElementRepository elementRepositoryMock

	private ElementRestQuery elementRestQuery

	void setup() {
		elementRestMapperMock = Mock()
		pageMapperMock = Mock()
		elementRepositoryMock = Mock()
		elementRestQuery = new ElementRestQuery(elementRestMapperMock, pageMapperMock, elementRepositoryMock)
	}

	void "maps ElementRestBeanParams to ElementRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		ElementRestBeanParams elementRestBeanParams = Mock()
		ElementRequestDTO elementRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = elementRestQuery.query(elementRestBeanParams)

		then:
		1 * elementRestMapperMock.mapBase(elementRestBeanParams) >> elementRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(elementRestBeanParams) >> pageRequest
		1 * elementRepositoryMock.findMatching(elementRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
