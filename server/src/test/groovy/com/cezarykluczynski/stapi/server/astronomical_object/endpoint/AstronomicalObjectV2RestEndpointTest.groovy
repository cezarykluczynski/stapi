package com.cezarykluczynski.stapi.server.astronomical_object.endpoint

import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2BaseResponse
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2FullResponse
import com.cezarykluczynski.stapi.server.astronomical_object.dto.AstronomicalObjectV2RestBeanParams
import com.cezarykluczynski.stapi.server.astronomical_object.reader.AstronomicalObjectV2RestReader
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest

class AstronomicalObjectV2RestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private AstronomicalObjectV2RestReader astronomicalObjectV2RestReaderMock

	private AstronomicalObjectV2RestEndpoint astronomicalObjectV2RestEndpoint

	void setup() {
		astronomicalObjectV2RestReaderMock = Mock()
		astronomicalObjectV2RestEndpoint = new AstronomicalObjectV2RestEndpoint(astronomicalObjectV2RestReaderMock)
	}

	void "passes get call to AstronomicalObjectRestReader"() {
		given:
		AstronomicalObjectV2FullResponse astronomicalObjectV2FullResponse = Mock()

		when:
		AstronomicalObjectV2FullResponse astronomicalObjectV2FullResponseOutput = astronomicalObjectV2RestEndpoint.getAstronomicalObject(UID)

		then:
		1 * astronomicalObjectV2RestReaderMock.readFull(UID) >> astronomicalObjectV2FullResponse
		astronomicalObjectV2FullResponseOutput == astronomicalObjectV2FullResponse
	}

	void "passes search get call to AstronomicalObjectRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		AstronomicalObjectV2BaseResponse astronomicalObjectV2Response = Mock()

		when:
		AstronomicalObjectV2BaseResponse astronomicalObjectV2ResponseOutput = astronomicalObjectV2RestEndpoint
				.searchAstronomicalObject(pageAwareBeanParams)

		then:
		1 * astronomicalObjectV2RestReaderMock.readBase(_ as AstronomicalObjectV2RestBeanParams) >> {
				AstronomicalObjectV2RestBeanParams astronomicalObjectV2RestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			astronomicalObjectV2Response
		}
		astronomicalObjectV2ResponseOutput == astronomicalObjectV2Response
	}

	void "passes search post call to AstronomicalObjectRestReader"() {
		given:
		AstronomicalObjectV2RestBeanParams astronomicalObjectV2RestBeanParams = new AstronomicalObjectV2RestBeanParams(name: NAME)
		AstronomicalObjectV2BaseResponse astronomicalObjectV2Response = Mock()

		when:
		AstronomicalObjectV2BaseResponse astronomicalObjectV2ResponseOutput = astronomicalObjectV2RestEndpoint
				.searchAstronomicalObject(astronomicalObjectV2RestBeanParams)

		then:
		1 * astronomicalObjectV2RestReaderMock.readBase(astronomicalObjectV2RestBeanParams as AstronomicalObjectV2RestBeanParams) >> {
				AstronomicalObjectV2RestBeanParams params ->
			assert params.name == NAME
			astronomicalObjectV2Response
		}
		astronomicalObjectV2ResponseOutput == astronomicalObjectV2Response
	}

}
