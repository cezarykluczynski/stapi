package com.cezarykluczynski.stapi.server.astronomicalObject.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectFullResponse
import com.cezarykluczynski.stapi.server.astronomicalObject.dto.AstronomicalObjectRestBeanParams
import com.cezarykluczynski.stapi.server.astronomicalObject.reader.AstronomicalObjectRestReader
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest

class AstronomicalObjectRestEndpointTest extends AbstractRestEndpointTest {

	private static final String GUID = 'GUID'
	private static final String NAME = 'NAME'

	private AstronomicalObjectRestReader astronomicalObjectRestReaderMock

	private AstronomicalObjectRestEndpoint astronomicalObjectRestEndpoint

	void setup() {
		astronomicalObjectRestReaderMock = Mock(AstronomicalObjectRestReader)
		astronomicalObjectRestEndpoint = new AstronomicalObjectRestEndpoint(astronomicalObjectRestReaderMock)
	}

	void "passes get call to AstronomicalObjectRestReader"() {
		given:
		AstronomicalObjectFullResponse astronomicalObjectFullResponse = Mock(AstronomicalObjectFullResponse)

		when:
		AstronomicalObjectFullResponse astronomicalObjectFullResponseOutput = astronomicalObjectRestEndpoint.getAstronomicalObject(GUID)

		then:
		1 * astronomicalObjectRestReaderMock.readFull(GUID) >> astronomicalObjectFullResponse
		astronomicalObjectFullResponseOutput == astronomicalObjectFullResponse
	}

	void "passes search get call to AstronomicalObjectRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock(PageSortBeanParams)
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		AstronomicalObjectBaseResponse astronomicalObjectResponse = Mock(AstronomicalObjectBaseResponse)

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
		AstronomicalObjectBaseResponse astronomicalObjectResponse = Mock(AstronomicalObjectBaseResponse)

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
