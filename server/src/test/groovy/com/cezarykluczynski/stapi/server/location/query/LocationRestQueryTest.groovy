package com.cezarykluczynski.stapi.server.location.query

import com.cezarykluczynski.stapi.model.location.dto.LocationRequestDTO
import com.cezarykluczynski.stapi.model.location.repository.LocationRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.location.dto.LocationRestBeanParams
import com.cezarykluczynski.stapi.server.location.mapper.LocationBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class LocationRestQueryTest extends Specification {

	private LocationBaseRestMapper locationRestMapperMock

	private PageMapper pageMapperMock

	private LocationRepository locationRepositoryMock

	private LocationRestQuery locationRestQuery

	void setup() {
		locationRestMapperMock = Mock()
		pageMapperMock = Mock()
		locationRepositoryMock = Mock()
		locationRestQuery = new LocationRestQuery(locationRestMapperMock, pageMapperMock, locationRepositoryMock)
	}

	void "maps LocationRestBeanParams to LocationRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		LocationRestBeanParams locationRestBeanParams = Mock()
		LocationRequestDTO locationRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = locationRestQuery.query(locationRestBeanParams)

		then:
		1 * locationRestMapperMock.mapBase(locationRestBeanParams) >> locationRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(locationRestBeanParams) >> pageRequest
		1 * locationRepositoryMock.findMatching(locationRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
