package com.cezarykluczynski.stapi.server.location.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.LocationV2BaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationV2FullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.location.dto.LocationV2RestBeanParams
import com.cezarykluczynski.stapi.server.location.reader.LocationV2RestReader

class LocationV2RestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private LocationV2RestReader locationV2RestReaderMock

	private LocationV2RestEndpoint locationV2RestEndpoint

	void setup() {
		locationV2RestReaderMock = Mock()
		locationV2RestEndpoint = new LocationV2RestEndpoint(locationV2RestReaderMock)
	}

	void "passes get call to LocationV2RestReader"() {
		given:
		LocationV2FullResponse locationV2FullResponse = Mock()

		when:
		LocationV2FullResponse locationV2FullResponseOutput = locationV2RestEndpoint.getLocation(UID)

		then:
		1 * locationV2RestReaderMock.readFull(UID) >> locationV2FullResponse
		locationV2FullResponseOutput == locationV2FullResponse
	}

	void "passes search get call to LocationV2RestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		LocationV2BaseResponse locationV2Response = Mock()

		when:
		LocationV2BaseResponse locationV2ResponseOutput = locationV2RestEndpoint.searchLocations(pageAwareBeanParams)

		then:
		1 * locationV2RestReaderMock.readBase(_ as LocationV2RestBeanParams) >> { LocationV2RestBeanParams locationV2RestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			locationV2Response
		}
		locationV2ResponseOutput == locationV2Response
	}

	void "passes search post call to LocationV2RestReader"() {
		given:
		LocationV2RestBeanParams locationV2RestBeanParams = new LocationV2RestBeanParams(name: NAME)
		LocationV2BaseResponse locationV2Response = Mock()

		when:
		LocationV2BaseResponse locationV2ResponseOutput = locationV2RestEndpoint.searchLocations(locationV2RestBeanParams)

		then:
		1 * locationV2RestReaderMock.readBase(locationV2RestBeanParams as LocationV2RestBeanParams) >> { LocationV2RestBeanParams params ->
			assert params.name == NAME
			locationV2Response
		}
		locationV2ResponseOutput == locationV2Response
	}

}
