package com.cezarykluczynski.stapi.server.astronomical_object.query

import com.cezarykluczynski.stapi.model.astronomical_object.dto.AstronomicalObjectRequestDTO
import com.cezarykluczynski.stapi.model.astronomical_object.repository.AstronomicalObjectRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.astronomical_object.dto.AstronomicalObjectRestBeanParams
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class AstronomicalObjectRestQueryTest extends Specification {

	private AstronomicalObjectBaseRestMapper astronomicalObjectRestMapperMock

	private PageMapper pageMapperMock

	private AstronomicalObjectRepository astronomicalObjectRepositoryMock

	private AstronomicalObjectRestQuery astronomicalObjectRestQuery

	void setup() {
		astronomicalObjectRestMapperMock = Mock()
		pageMapperMock = Mock()
		astronomicalObjectRepositoryMock = Mock()
		astronomicalObjectRestQuery = new AstronomicalObjectRestQuery(astronomicalObjectRestMapperMock, pageMapperMock,
				astronomicalObjectRepositoryMock)
	}

	void "maps AstronomicalObjectRestBeanParams to AstronomicalObjectRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams = Mock()
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = astronomicalObjectRestQuery.query(astronomicalObjectRestBeanParams)

		then:
		1 * astronomicalObjectRestMapperMock.mapBase(astronomicalObjectRestBeanParams) >> astronomicalObjectRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(astronomicalObjectRestBeanParams) >> pageRequest
		1 * astronomicalObjectRepositoryMock.findMatching(astronomicalObjectRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
