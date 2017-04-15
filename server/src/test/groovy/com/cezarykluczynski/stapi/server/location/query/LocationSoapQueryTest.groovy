package com.cezarykluczynski.stapi.server.location.query

import com.cezarykluczynski.stapi.client.v1.soap.LocationBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.LocationFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.model.location.dto.LocationRequestDTO
import com.cezarykluczynski.stapi.model.location.repository.LocationRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.location.mapper.LocationBaseSoapMapper
import com.cezarykluczynski.stapi.server.location.mapper.LocationFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class LocationSoapQueryTest extends Specification {

	private LocationBaseSoapMapper locationBaseSoapMapperMock

	private LocationFullSoapMapper locationFullSoapMapperMock

	private PageMapper pageMapperMock

	private LocationRepository locationRepositoryMock

	private LocationSoapQuery locationSoapQuery

	void setup() {
		locationBaseSoapMapperMock = Mock()
		locationFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		locationRepositoryMock = Mock()
		locationSoapQuery = new LocationSoapQuery(locationBaseSoapMapperMock, locationFullSoapMapperMock, pageMapperMock, locationRepositoryMock)
	}

	void "maps LocationBaseRequest to LocationRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		LocationBaseRequest locationRequest = Mock()
		locationRequest.page >> requestPage
		LocationRequestDTO locationRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = locationSoapQuery.query(locationRequest)

		then:
		1 * locationBaseSoapMapperMock.mapBase(locationRequest) >> locationRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * locationRepositoryMock.findMatching(locationRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps LocationFullRequest to LocationRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		LocationFullRequest locationRequest = Mock()
		LocationRequestDTO locationRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = locationSoapQuery.query(locationRequest)

		then:
		1 * locationFullSoapMapperMock.mapFull(locationRequest) >> locationRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * locationRepositoryMock.findMatching(locationRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
