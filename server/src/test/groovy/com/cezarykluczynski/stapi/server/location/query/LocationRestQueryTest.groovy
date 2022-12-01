package com.cezarykluczynski.stapi.server.location.query

import com.cezarykluczynski.stapi.model.location.repository.LocationRepository
import com.cezarykluczynski.stapi.model.location.dto.LocationRequestDTO
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.location.dto.LocationRestBeanParams
import com.cezarykluczynski.stapi.server.location.mapper.LocationBaseRestMapper
import com.cezarykluczynski.stapi.server.location.dto.LocationV2RestBeanParams
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class LocationRestQueryTest extends Specification {

	private LocationBaseRestMapper locationBaseRestMapperMock

	private PageMapper pageMapperMock

	private LocationRepository locationRepositoryMock

	private LocationRestQuery locationRestQuery

	void setup() {
		locationBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		locationRepositoryMock = Mock()
		locationRestQuery = new LocationRestQuery(locationBaseRestMapperMock, pageMapperMock, locationRepositoryMock)
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
		1 * locationBaseRestMapperMock.mapBase(locationRestBeanParams) >> locationRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(locationRestBeanParams) >> pageRequest
		1 * locationRepositoryMock.findMatching(locationRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps LocationV2RestBeanParams to LocationRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		LocationV2RestBeanParams locationV2RestBeanParams = Mock()
		LocationRequestDTO locationRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = locationRestQuery.query(locationV2RestBeanParams)

		then:
		1 * locationBaseRestMapperMock.mapV2Base(locationV2RestBeanParams) >> locationRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(locationV2RestBeanParams) >> pageRequest
		1 * locationRepositoryMock.findMatching(locationRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
