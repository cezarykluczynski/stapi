package com.cezarykluczynski.stapi.server.element.query

import com.cezarykluczynski.stapi.model.element.dto.ElementRequestDTO
import com.cezarykluczynski.stapi.model.element.repository.ElementRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.element.dto.ElementRestBeanParams
import com.cezarykluczynski.stapi.server.element.dto.ElementV2RestBeanParams
import com.cezarykluczynski.stapi.server.element.mapper.ElementBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class ElementRestQueryTest extends Specification {

	private ElementBaseRestMapper elementBaseRestMapperMock

	private PageMapper pageMapperMock

	private ElementRepository elementRepositoryMock

	private ElementRestQuery elementRestQuery

	void setup() {
		elementBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		elementRepositoryMock = Mock()
		elementRestQuery = new ElementRestQuery(elementBaseRestMapperMock, pageMapperMock, elementRepositoryMock)
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
		1 * elementBaseRestMapperMock.mapBase(elementRestBeanParams) >> elementRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(elementRestBeanParams) >> pageRequest
		1 * elementRepositoryMock.findMatching(elementRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps ElementV2RestBeanParams to ElementRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		ElementV2RestBeanParams elementV2RestBeanParams = Mock()
		ElementRequestDTO elementRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = elementRestQuery.query(elementV2RestBeanParams)

		then:
		1 * elementBaseRestMapperMock.mapV2Base(elementV2RestBeanParams) >> elementRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(elementV2RestBeanParams) >> pageRequest
		1 * elementRepositoryMock.findMatching(elementRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
