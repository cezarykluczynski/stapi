package com.cezarykluczynski.stapi.server.astronomicalObject.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.astronomicalObject.dto.AstronomicalObjectRestBeanParams
import com.cezarykluczynski.stapi.server.astronomicalObject.reader.AstronomicalObjectRestReader

class AstronomicalObjectRestEndpointTest extends AbstractRestEndpointTest {

	private AstronomicalObjectRestReader astronomicalObjectRestReaderMock

	private AstronomicalObjectRestEndpoint astronomicalObjectRestEndpoint

	void setup() {
		astronomicalObjectRestReaderMock = Mock(AstronomicalObjectRestReader)
		astronomicalObjectRestEndpoint = new AstronomicalObjectRestEndpoint(astronomicalObjectRestReaderMock)
	}

	void "passes get call to AstronomicalObjectRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams)
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		AstronomicalObjectResponse astronomicalObjectResponse = Mock(AstronomicalObjectResponse)

		when:
		AstronomicalObjectResponse astronomicalObjectResponseOutput = astronomicalObjectRestEndpoint.getAstronomicalObjects(pageAwareBeanParams)

		then:
		1 * astronomicalObjectRestReaderMock.read(_ as AstronomicalObjectRestBeanParams) >> {
			AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			astronomicalObjectResponse
		}
		astronomicalObjectResponseOutput == astronomicalObjectResponse
	}

	void "passes post call to AstronomicalObjectRestReader"() {
		given:
		AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams = new AstronomicalObjectRestBeanParams()
		AstronomicalObjectResponse astronomicalObjectResponse = Mock(AstronomicalObjectResponse)

		when:
		AstronomicalObjectResponse astronomicalObjectResponseOutput = astronomicalObjectRestEndpoint
				.searchAstronomicalObjects(astronomicalObjectRestBeanParams)

		then:
		1 * astronomicalObjectRestReaderMock.read(astronomicalObjectRestBeanParams as AstronomicalObjectRestBeanParams) >> {
			AstronomicalObjectRestBeanParams params ->
			astronomicalObjectResponse
		}
		astronomicalObjectResponseOutput == astronomicalObjectResponse
	}

}
