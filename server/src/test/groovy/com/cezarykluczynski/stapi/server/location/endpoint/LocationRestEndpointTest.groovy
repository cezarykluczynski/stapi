package com.cezarykluczynski.stapi.server.location.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.LocationBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.location.dto.LocationRestBeanParams
import com.cezarykluczynski.stapi.server.location.reader.LocationRestReader

class LocationRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private LocationRestReader locationRestReaderMock

	private LocationRestEndpoint locationRestEndpoint

	void setup() {
		locationRestReaderMock = Mock()
		locationRestEndpoint = new LocationRestEndpoint(locationRestReaderMock)
	}

	void "passes get call to LocationRestReader"() {
		given:
		LocationFullResponse locationFullResponse = Mock()

		when:
		LocationFullResponse locationFullResponseOutput = locationRestEndpoint.getLocation(UID)

		then:
		1 * locationRestReaderMock.readFull(UID) >> locationFullResponse
		locationFullResponseOutput == locationFullResponse
	}

	void "passes search get call to LocationRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		LocationBaseResponse locationResponse = Mock()

		when:
		LocationBaseResponse locationResponseOutput = locationRestEndpoint.searchLocations(pageAwareBeanParams)

		then:
		1 * locationRestReaderMock.readBase(_ as LocationRestBeanParams) >> { LocationRestBeanParams locationRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			locationResponse
		}
		locationResponseOutput == locationResponse
	}

	void "passes search post call to LocationRestReader"() {
		given:
		LocationRestBeanParams locationRestBeanParams = new LocationRestBeanParams(name: NAME)
		LocationBaseResponse locationResponse = Mock()

		when:
		LocationBaseResponse locationResponseOutput = locationRestEndpoint.searchLocations(locationRestBeanParams)

		then:
		1 * locationRestReaderMock.readBase(locationRestBeanParams as LocationRestBeanParams) >> { LocationRestBeanParams params ->
			assert params.name == NAME
			locationResponse
		}
		locationResponseOutput == locationResponse
	}

}
