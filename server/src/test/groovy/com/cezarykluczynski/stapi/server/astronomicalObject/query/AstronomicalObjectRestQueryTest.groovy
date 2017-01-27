package com.cezarykluczynski.stapi.server.astronomicalObject.query

import com.cezarykluczynski.stapi.model.astronomicalObject.dto.AstronomicalObjectRequestDTO
import com.cezarykluczynski.stapi.model.astronomicalObject.repository.AstronomicalObjectRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.astronomicalObject.dto.AstronomicalObjectRestBeanParams
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class AstronomicalObjectRestQueryTest extends Specification {

	private AstronomicalObjectRestMapper astronomicalObjectRestMapperMock

	private PageMapper pageMapperMock

	private AstronomicalObjectRepository astronomicalObjectRepositoryMock

	private AstronomicalObjectRestQuery astronomicalObjectRestQuery

	void setup() {
		astronomicalObjectRestMapperMock = Mock(AstronomicalObjectRestMapper)
		pageMapperMock = Mock(PageMapper)
		astronomicalObjectRepositoryMock = Mock(AstronomicalObjectRepository)
		astronomicalObjectRestQuery = new AstronomicalObjectRestQuery(astronomicalObjectRestMapperMock, pageMapperMock,
				astronomicalObjectRepositoryMock)
	}

	void "maps AstronomicalObjectRestBeanParams to AstronomicalObjectRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock(PageRequest)
		AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams = Mock(AstronomicalObjectRestBeanParams) {

		}
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = Mock(AstronomicalObjectRequestDTO)
		Page page = Mock(Page)

		when:
		Page pageOutput = astronomicalObjectRestQuery.query(astronomicalObjectRestBeanParams)

		then:
		1 * astronomicalObjectRestMapperMock.map(astronomicalObjectRestBeanParams) >> astronomicalObjectRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(astronomicalObjectRestBeanParams) >> pageRequest
		1 * astronomicalObjectRepositoryMock.findMatching(astronomicalObjectRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
