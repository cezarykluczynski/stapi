package com.cezarykluczynski.stapi.server.astronomical_object.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectFullResponse
import com.cezarykluczynski.stapi.server.astronomical_object.dto.AstronomicalObjectRestBeanParams
import com.cezarykluczynski.stapi.server.astronomical_object.reader.AstronomicalObjectRestReader
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest

class AstronomicalObjectRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private AstronomicalObjectRestReader astronomicalObjectRestReaderMock

	private AstronomicalObjectRestEndpoint astronomicalObjectRestEndpoint

	void setup() {
		astronomicalObjectRestReaderMock = Mock()
		astronomicalObjectRestEndpoint = new AstronomicalObjectRestEndpoint(astronomicalObjectRestReaderMock)
	}

	void "passes get call to AstronomicalObjectRestReader"() {
		given:
		AstronomicalObjectFullResponse astronomicalObjectFullResponse = Mock()

		when:
		AstronomicalObjectFullResponse astronomicalObjectFullResponseOutput = astronomicalObjectRestEndpoint.getAstronomicalObject(UID)

		then:
		1 * astronomicalObjectRestReaderMock.readFull(UID) >> astronomicalObjectFullResponse
		astronomicalObjectFullResponseOutput == astronomicalObjectFullResponse
	}

	void "passes search get call to AstronomicalObjectRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		AstronomicalObjectBaseResponse astronomicalObjectResponse = Mock()

		when:
		AstronomicalObjectBaseResponse astronomicalObjectResponseOutput = astronomicalObjectRestEndpoint
				.searchAstronomicalObject(pageAwareBeanParams)

		then:
		1 * astronomicalObjectRestReaderMock.readBase(_ as AstronomicalObjectRestBeanParams) >> {
				AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			astronomicalObjectResponse
		}
		astronomicalObjectResponseOutput == astronomicalObjectResponse
	}

	void "passes search post call to AstronomicalObjectRestReader"() {
		given:
		AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams = new AstronomicalObjectRestBeanParams(name: NAME)
		AstronomicalObjectBaseResponse astronomicalObjectResponse = Mock()

		when:
		AstronomicalObjectBaseResponse astronomicalObjectResponseOutput = astronomicalObjectRestEndpoint
				.searchAstronomicalObject(astronomicalObjectRestBeanParams)

		then:
		1 * astronomicalObjectRestReaderMock.readBase(astronomicalObjectRestBeanParams as AstronomicalObjectRestBeanParams) >> {
				AstronomicalObjectRestBeanParams params ->
			assert params.name == NAME
			astronomicalObjectResponse
		}
		astronomicalObjectResponseOutput == astronomicalObjectResponse
	}

}
