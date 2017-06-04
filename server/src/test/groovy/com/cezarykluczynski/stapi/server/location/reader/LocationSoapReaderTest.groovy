package com.cezarykluczynski.stapi.server.location.reader

import com.cezarykluczynski.stapi.client.v1.soap.LocationBase
import com.cezarykluczynski.stapi.client.v1.soap.LocationBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.LocationBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.LocationFull
import com.cezarykluczynski.stapi.client.v1.soap.LocationFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.LocationFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.model.location.entity.Location
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.location.mapper.LocationBaseSoapMapper
import com.cezarykluczynski.stapi.server.location.mapper.LocationFullSoapMapper
import com.cezarykluczynski.stapi.server.location.query.LocationSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class LocationSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private LocationSoapQuery locationSoapQueryBuilderMock

	private LocationBaseSoapMapper locationBaseSoapMapperMock

	private LocationFullSoapMapper locationFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private LocationSoapReader locationSoapReader

	void setup() {
		locationSoapQueryBuilderMock = Mock()
		locationBaseSoapMapperMock = Mock()
		locationFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		locationSoapReader = new LocationSoapReader(locationSoapQueryBuilderMock, locationBaseSoapMapperMock, locationFullSoapMapperMock,
				pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<Location> locationList = Lists.newArrayList()
		Page<Location> locationPage = Mock()
		List<LocationBase> soapLocationList = Lists.newArrayList(new LocationBase(uid: UID))
		LocationBaseRequest locationBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		LocationBaseResponse locationResponse = locationSoapReader.readBase(locationBaseRequest)

		then:
		1 * locationSoapQueryBuilderMock.query(locationBaseRequest) >> locationPage
		1 * locationPage.content >> locationList
		1 * pageMapperMock.fromPageToSoapResponsePage(locationPage) >> responsePage
		1 * locationBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * locationBaseSoapMapperMock.mapBase(locationList) >> soapLocationList
		0 * _
		locationResponse.locations[0].uid == UID
		locationResponse.page == responsePage
		locationResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		LocationFull locationFull = new LocationFull(uid: UID)
		Location location = Mock()
		Page<Location> locationPage = Mock()
		LocationFullRequest locationFullRequest = new LocationFullRequest(uid: UID)

		when:
		LocationFullResponse locationFullResponse = locationSoapReader.readFull(locationFullRequest)

		then:
		1 * locationSoapQueryBuilderMock.query(locationFullRequest) >> locationPage
		1 * locationPage.content >> Lists.newArrayList(location)
		1 * locationFullSoapMapperMock.mapFull(location) >> locationFull
		0 * _
		locationFullResponse.location.uid == UID
	}

	void "requires UID in full request"() {
		given:
		LocationFullRequest locationFullRequest = Mock()

		when:
		locationSoapReader.readFull(locationFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
